package com.vibent.vibentback.user;

import com.vibent.vibentback.api.user.EmailChangeRequest;
import com.vibent.vibentback.api.user.UpdateUserRequest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.mail.MailService;
import com.vibent.vibentback.common.util.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {

    private final ConnectedUserUtils connectedUserUtils;
    private final UserRepository userRepository;
    private final MailService mailService;

    @Value("${vibent.mail.autoConfirm}")
    private boolean AUTO_CONFIRM_MAIL;

    public User getUserByRef(String ref) {
        return userRepository.findByRef(ref)
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String userRef) {
        userRepository.deleteByRef(userRef);
    }

    public User updateUser(String userRef, UpdateUserRequest request) {
        User existing = userRepository.findByRef(userRef)
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        if (request.getFirstName() != null) existing.setFirstName(request.getFirstName());
        if (request.getLastName() != null) existing.setLastName(request.getLastName());
        if (request.getPassword() != null)
            existing.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        return userRepository.save(existing);
    }

    public boolean existsByRef(String ref) {
        return userRepository.existsByRef(ref);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public User getConnectedUser() {
        return connectedUserUtils.getConnectedUser();
    }

    /**
     * For spring security. It is important to note that in our application
     * the user ref is his email.
     */
    @Override
    public UserDetails loadUserByUsername(String ref) throws UsernameNotFoundException {
        return getUserByRef(ref);
    }

    public String confirmEmail(TokenInfo info) {
        User user = userRepository.findById(info.getId())
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));

        // First time confirming email
        if (!user.isEnabled()) {
            if (user.getEmail().equals(info.getBody())) {
                user.setEnabled(true);
                userRepository.save(user);
            } else {
                throw new VibentException(VibentError.INVALID_CONFIRMED_EMAIL);
            }
        }

        // User is changing email
        else {
            user.setEmail(info.getBody());
            userRepository.save(user);
        }
        return user.getEmail();
    }

    public String changeEmail(EmailChangeRequest request) {
        mailService.sendChangeEmailConfirmationMail(connectedUserUtils.getConnectedUser(), request.getEmail());

        if (AUTO_CONFIRM_MAIL) {
            this.confirmEmail(new TokenInfo(connectedUserUtils.getConnectedUser().getId(), request.getEmail()));
        }
        return request.getEmail();
    }
}
