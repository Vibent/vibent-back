package com.vibent.vibentback.common.util;

import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Utils used for general tokens like group invite tokens and email confirmation tokens
 */
@Component
public class TokenUtils {

    private final static String EMAIL_CONFIRM_PREFIX = "e";
    private final static int EMAIL_CONFIRM_EXPIRY_HOURS = 2; // 1h < X < 2h

    private final static String GROUP_INVITE_PREFIX = "g";
    private final static int GROUP_INVITE_EXPIRY_HOURS = 169; // 1W < X < 1W1H

    private final static String STANDALONE_EVENT_INVITE_PREFIX = "v";
    private final static int STANDALONE_EVENT_INVITE_EXPIRY_HOURS = 169; // 1W < X < 1W1H

    private final static String PASSWORD_RESET_PREFIX = "p";
    private final static int PASSWORD_RESET_EXPIRY_HOURS = 2; // 1h < X < 2h

    private static final long SECONDS_TO_HOURS = 3600;

    @Autowired
    AESUtils aesUtils;

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

    public String getEmailConfirmToken(Long userId, String email) {
        String toEncrypt = EMAIL_CONFIRM_PREFIX + String.valueOf(userId)
                + " " + email
                + " " + getExpiryTime(EMAIL_CONFIRM_EXPIRY_HOURS);
        return aesUtils.encrypt(toEncrypt);
    }

    public TokenInfo readEmailConfirmToken(String encryptedToken) {
        String decrypted = aesUtils.decrypt(encryptedToken);

        if (!decrypted.startsWith(EMAIL_CONFIRM_PREFIX)) {
            throw new VibentException(VibentError.WRONG_TOKEN_TYPE);
        }
        decrypted = decrypted.substring(EMAIL_CONFIRM_PREFIX.length());

        String[] splitted = decrypted.split(" ");
        if (splitted.length != 3) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }

        checkExpiryTime(splitted[2]);

        Long userId;
        try {
            userId = Long.valueOf(splitted[0]);
        } catch (NumberFormatException e) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }
        return new TokenInfo(userId, splitted[1]);
    }

    public String getGroupInviteToken(Long groupId) {
        String toEncrypt = GROUP_INVITE_PREFIX + String.valueOf(groupId)
                + " " + getExpiryTime(GROUP_INVITE_EXPIRY_HOURS);
        return aesUtils.encrypt(toEncrypt);
    }

    public TokenInfo readGroupInviteToken(String encryptedToken) {
        String decrypted = aesUtils.decrypt(encryptedToken);

        if (!decrypted.startsWith(GROUP_INVITE_PREFIX)) {
            throw new VibentException(VibentError.WRONG_TOKEN_TYPE);
        }
        decrypted = decrypted.substring(GROUP_INVITE_PREFIX.length());

        String[] splitted = decrypted.split(" ");
        if (splitted.length != 2) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }

        checkExpiryTime(splitted[1]);

        Long groupId;
        try {
            groupId = Long.valueOf(splitted[0]);
        } catch (NumberFormatException e) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }
        return new TokenInfo(groupId, null);
    }

    public String getStandaloneEventInviteToken(Long eventId) {
        String toEncrypt = STANDALONE_EVENT_INVITE_PREFIX + String.valueOf(eventId)
                + " " + getExpiryTime(STANDALONE_EVENT_INVITE_EXPIRY_HOURS);
        return aesUtils.encrypt(toEncrypt);
    }

    public TokenInfo readStandaloneEventInviteToken(String encryptedToken) {
        String decrypted = aesUtils.decrypt(encryptedToken);

        if (!decrypted.startsWith(STANDALONE_EVENT_INVITE_PREFIX)) {
            throw new VibentException(VibentError.WRONG_TOKEN_TYPE);
        }
        decrypted = decrypted.substring(STANDALONE_EVENT_INVITE_PREFIX.length());

        String[] splitted = decrypted.split(" ");
        if (splitted.length != 2) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }

        checkExpiryTime(splitted[1]);

        Long eventId;
        try {
            eventId = Long.valueOf(splitted[0]);
        } catch (NumberFormatException e) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }
        return new TokenInfo(eventId, null);
    }

    public String getPasswordResetToken(Long userId) {
        String toEncrypt = PASSWORD_RESET_PREFIX + String.valueOf(userId)
                + " " + getExpiryTime(PASSWORD_RESET_EXPIRY_HOURS);
        return aesUtils.encrypt(toEncrypt);
    }

    public TokenInfo readPasswordResetToken(String encryptedToken) {
        String decrypted = aesUtils.decrypt(encryptedToken);

        if (!decrypted.startsWith(PASSWORD_RESET_PREFIX)) {
            throw new VibentException(VibentError.WRONG_TOKEN_TYPE);
        }
        decrypted = decrypted.substring(PASSWORD_RESET_PREFIX.length());

        String[] splitted = decrypted.split(" ");
        if (splitted.length != 2) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }

        checkExpiryTime(splitted[1]);

        Long userId;
        try {
            userId = Long.valueOf(splitted[0]);
        } catch (NumberFormatException e) {
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        }
        return new TokenInfo(userId, null);
    }
}
