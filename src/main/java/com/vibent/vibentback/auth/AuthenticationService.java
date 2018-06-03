package com.vibent.vibentback.auth;

import com.vibent.vibentback.MockService;
import com.vibent.vibentback.api.auth.EmailLoginRequest;
import com.vibent.vibentback.api.auth.RegistrationRequest;
import com.vibent.vibentback.api.auth.PhoneLoginRequest;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import com.vibent.vibentback.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationService {

    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private MockService mockService;
    private UserRepository userRepository;
    private TokenUtils tokenUtils;

    private String createToken(User user){
        Authentication authentication = new VibentAuthentication(user.getRef(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return this.tokenUtils.createJWTToken((String) authentication.getPrincipal());
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

        return createToken(user);
    }

    public String loginPhone(PhoneLoginRequest request) {
        User user = this.userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new VibentException(VibentError.AUTH_USER_NOT_FOUND));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new VibentException(VibentError.AUTHENTICATION_FAILED);

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

        mockService.sendConfirmationMail(user);

        return user;
    }


    @Bean
    private BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder(10);
    }
}
