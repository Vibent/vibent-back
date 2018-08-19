package com.vibent.vibentback.auth;

import com.vibent.vibentback.api.auth.EmailLoginRequest;
import com.vibent.vibentback.api.auth.RegistrationRequest;
import com.vibent.vibentback.api.auth.PhoneLoginRequest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.mail.MailService;
import com.vibent.vibentback.common.util.TokenUtils;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import com.vibent.vibentback.user.UserService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private final TokenUtils tokenUtils;
    private final MailService mailService;

    private String createToken(User user){
        Authentication authentication = new VibentAuthentication(user.getRef(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return this.tokenUtils.createUserAuthenticationToken((String) authentication.getPrincipal());
    }

    /**
     * Email login - throws exception if login failed
     * @param request the authentication request
     * @return a token if authentication is successful
     */
    public String loginEmail(EmailLoginRequest request){
        User user = this.userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new VibentException(VibentError.AUTH_USER_NOT_FOUND));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new VibentException(VibentError.AUTHENTICATION_FAILED);
        if(!user.isEnabled()){
            throw new VibentException(VibentError.USER_EMAIL_NOT_CONFIRMED);
        }
        return createToken(user);
    }

    public String loginPhone(PhoneLoginRequest request) {
        User user = this.userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new VibentException(VibentError.AUTH_USER_NOT_FOUND));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new VibentException(VibentError.AUTHENTICATION_FAILED);
        if(!user.isEnabled()){
            throw new VibentException(VibentError.USER_EMAIL_NOT_CONFIRMED);
        }
        return createToken(user);
    }

    public User register(RegistrationRequest request){
        if (userService.existsByEmail(request.getEmail())) {
            throw new VibentException(VibentError.USER_ALREADY_EXISTS);
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setLastPasswordReset(new Date());
        user.setRef(UUID.randomUUID().toString());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        userService.addUser(user);
        // mailService.sendConfirmationMail(user);

        if(AUTO_CONFIRM_MAIL) {
            userService.confirmEmail(user);
        }

        return user;
    }

    public String confirmEmail(String token) {
        Claims claims = tokenUtils.validateJWTToken(token);
        String claimsSubject = claims.getSubject();
        if(!claimsSubject.startsWith("confirmMail:"))
            throw new VibentException(VibentError.MALFORMED_TOKEN);
        String email = claimsSubject.substring("confirmMail:".length());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        userService.confirmEmail(user);
        return user.getEmail();
    }
}
