package com.vibent.vibentback.common.util;

import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class TokenUtils {

    private final static String EMAIL_CONFIRM_PREFIX = "e";
    private final static int EMAIL_CONFIRM_EXPIRY_HOURS = 2; // 1h < X < 2h
    private final static int EMAIL_CONFIRM_SUBJECT_COUNT = 4; // prefix/expiryDate/email/userId

    private final static String EVENT_INVITE_PREFIX = "v";
    private final static int EVENT_INVITE_EXPIRY_HOURS = 169; // 1W < X < 1W1H
    private final static int EVENT_INVITE_SUBJECT_COUNT = 3; // prefix/expiryDate/eventId

    private final static String DISTRIBUTION_LIST_INVITE_PREFIX = "l";
    private final static int DISTRIBUTION_LIST_INVITE_EXPIRY_HOURS = 169; // 1W < X < 1W1H
    private final static int DISTRIBUTION_LIST_INVITE_SUBJECT_COUNT = 3; // prefix/expiryDate/distributionListId

    private final static String PASSWORD_RESET_PREFIX = "p";
    private final static int PASSWORD_RESET_EXPIRY_HOURS = 2; // 1h < X < 2h
    private final static int PASSWORD_RESET_SUBJECT_COUNT = 3; // prefix/expiryDate/userId


    private static final long SECONDS_TO_HOURS = 3600;

    @Autowired
    AESUtils aesUtils;

    public String getEmailConfirmToken(Long userId, String email) {
        return aesUtils.encrypt(EMAIL_CONFIRM_PREFIX, getExpiryTime(EMAIL_CONFIRM_EXPIRY_HOURS), email, String.valueOf(userId));
    }

    public TokenInfo readEmailConfirmToken(String encryptedToken) {
        String[] subjects = decrypt(encryptedToken, EMAIL_CONFIRM_PREFIX, EMAIL_CONFIRM_SUBJECT_COUNT);

        Long userId = toLong(subjects[3]);
        return new TokenInfo(userId, subjects[2]);
    }

    public String getEventInviteToken(Long eventId) {
        return aesUtils.encrypt(EVENT_INVITE_PREFIX, getExpiryTime(EVENT_INVITE_EXPIRY_HOURS), String.valueOf(eventId));
    }

    public TokenInfo readEventInviteToken(String encryptedToken) {
        String[] subjects = decrypt(encryptedToken, EVENT_INVITE_PREFIX, EVENT_INVITE_SUBJECT_COUNT);

        Long eventId = toLong(subjects[2]);
        return new TokenInfo(eventId, null);
    }

    public String getPasswordResetToken(Long userId) {
        return aesUtils.encrypt(PASSWORD_RESET_PREFIX, getExpiryTime(PASSWORD_RESET_EXPIRY_HOURS), String.valueOf(userId));
    }

    public TokenInfo readPasswordResetToken(String encryptedToken) {
        String[] subjects = decrypt(encryptedToken, PASSWORD_RESET_PREFIX, PASSWORD_RESET_SUBJECT_COUNT);

        Long userId = toLong(subjects[2]);
        return new TokenInfo(userId, null);
    }

    public String getDistributionListInviteToken(Long distributionListId) {
        return aesUtils.encrypt(DISTRIBUTION_LIST_INVITE_PREFIX,
                getExpiryTime(DISTRIBUTION_LIST_INVITE_EXPIRY_HOURS),
                String.valueOf(distributionListId));
    }

    public TokenInfo readDistributionListInviteToken(String encryptedToken) {
        String[] subjects = decrypt(encryptedToken, DISTRIBUTION_LIST_INVITE_PREFIX, DISTRIBUTION_LIST_INVITE_SUBJECT_COUNT);

        Long distributionListId = toLong(subjects[2]);
        return new TokenInfo(distributionListId, null);
    }

    // Helper functions
    private String getExpiryTime(int hours) {
        Instant i = Instant.now().plus(hours, ChronoUnit.HOURS);
        return String.valueOf(i.getEpochSecond() / SECONDS_TO_HOURS);
    }

    private void checkExpiryTime(String expiryString) {
        try {
            long epochHours = Long.valueOf(expiryString);
            long epochSeconds = epochHours * SECONDS_TO_HOURS;
            Assert.isTrue(Instant.ofEpochSecond(epochSeconds).isAfter(Instant.now()), "Token expired");
        } catch (IllegalArgumentException e) {
            throw new VibentException(VibentError.TOKEN_EXPIRED);
        }
    }

    private String[] decrypt(String encryptedToken, String prefix, int subjectCount) {
        String decrypted = aesUtils.decrypt(encryptedToken);

        String[] splitted = decrypted.split(" ");
        if (splitted.length != subjectCount) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }
        if (!prefix.equals(splitted[0])) {
            throw new VibentException(VibentError.WRONG_TOKEN_TYPE);
        }

        checkExpiryTime(splitted[1]);

        return splitted;
    }

    private Long toLong(String subject) {
        try {
            return Long.valueOf(subject);
        } catch (NumberFormatException e) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }
    }
}
