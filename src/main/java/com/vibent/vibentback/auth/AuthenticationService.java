package com.vibent.vibentback.auth;

import com.vibent.vibentback.auth.api.*;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.mail.MailService;
import com.vibent.vibentback.common.util.JWTUtils;
import com.vibent.vibentback.common.util.TokenInfo;
import com.vibent.vibentback.common.util.TokenUtils;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import com.vibent.vibentback.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService {

    @Value("${vibent.mail.autoConfirm}")
    private boolean AUTO_CONFIRM_MAIL;

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JWTUtils JWTUtils;
    private final TokenUtils tokenUtils;
    private final MailService mailService;

    public String createToken(User user) {
        Authentication authentication = new VibentAuthentication(user.getRef(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return this.JWTUtils.createUserAuthenticationToken((String) authentication.getPrincipal());
    }

    public String loginApi(MultiValueMap<String, String> request) {
        String email = request.getFirst("client_id");
        String password = request.getFirst("client_secret");

        if (email == null || password == null) {
            throw new VibentException(VibentError.INVALID_BODY);
        }

        EmailLoginRequest emailLoginRequest = new EmailLoginRequest();
        emailLoginRequest.setEmail(email);
        emailLoginRequest.setPassword(password);
        return this.loginEmail(emailLoginRequest);
    }

    /**
     * Email login - throws exception if login failed
     *
     * @param request the authentication request
     * @return a token if authentication is successful
     */
    public String loginEmail(EmailLoginRequest request) {
        User user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new VibentException(VibentError.AUTHENTICATION_FAILED);
        if (!user.isEnabled()) {
            throw new VibentException(VibentError.USER_EMAIL_NOT_CONFIRMED);
        }
        return createToken(user);
    }

    public String loginPhone(PhoneLoginRequest request) {
        User user = this.userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new VibentException(VibentError.AUTHENTICATION_FAILED);
        if (!user.isEnabled()) {
            throw new VibentException(VibentError.USER_EMAIL_NOT_CONFIRMED);
        }
        return createToken(user);
    }

    public User register(RegistrationRequest request) {
        if (request.getEmail() == null && request.getPhoneNumber() == null) {
            throw new VibentException(VibentError.USER_NO_EMAIL_OR_PHONE);
        }

        if (request.getEmail() != null && userService.existsByEmail(request.getEmail())) {
            throw new VibentException(VibentError.USER_ALREADY_EXISTS);
        }
        if (request.getPhoneNumber() != null && userService.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new VibentException(VibentError.USER_ALREADY_EXISTS);
        }

        User user = new User();
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setLastPasswordReset(new Date());
        user.setRef(UUID.randomUUID().toString());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userService.addUser(user);
        mailService.sendConfirmationMail(user);

        if (AUTO_CONFIRM_MAIL) {
            userService.confirmEmail(new TokenInfo(user.getId(), user.getEmail()));
        }

        return user;
    }

    public String confirmEmail(String token) {
        TokenInfo info = tokenUtils.readEmailConfirmToken(token);
        return userService.confirmEmail(info);
    }

    public void requestPasswordReset(PasswordResetRequest request) {
        User user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        mailService.sendPasswordResetMail(user);
    }

    public void validatePasswordReset(ValidatePasswordResetRequest request) {
        TokenInfo info = tokenUtils.readPasswordResetToken(request.getToken());
        User user = this.userRepository.findById(info.getId())
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
