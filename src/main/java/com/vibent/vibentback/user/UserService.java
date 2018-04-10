package com.vibent.vibentback.user;

import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements UserDetailsService {

    ConnectedUserUtils connectedUserUtils;
    UserRepository userRepository;

    public User getUserByRef(String ref) {
        User user = userRepository.findByRef(ref)
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        return user;
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        return user;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String userRef) {
        userRepository.deleteByRef(userRef);
    }

    public User updateUser(String userRef, User newUser) {
        User existing = userRepository.findByRef(userRef)
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        // TODO
        return existing;
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public User getConnectedUser(){
        return connectedUserUtils.getConnectedUser();
    }

    // For Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }
}
