package com.vibent.vibentback.user;

import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.api.user.UpdateUserRequest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {

    ConnectedUserUtils connectedUserUtils;
    UserRepository userRepository;

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
        if(request.getEmail() != null) existing.setEmail(request.getEmail());
        // TODO Confirm mail
        if(request.getFirstName() != null) existing.setFirstName(request.getFirstName());
        if(request.getLastName() != null) existing.setLastName(request.getLastName());
        if(request.getPassword() != null) existing.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        return userRepository.save(existing);
    }

    public boolean existsByRef(String ref){
        return userRepository.existsByRef(ref);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public User getConnectedUser(){
        return connectedUserUtils.getConnectedUser();
    }

    /**
     * For spring security. It is important to note that in our application
     * the user ref is his username.
     */
    @Override
    public UserDetails loadUserByUsername(String ref) throws UsernameNotFoundException {
        return getUserByRef(ref);
    }
}
