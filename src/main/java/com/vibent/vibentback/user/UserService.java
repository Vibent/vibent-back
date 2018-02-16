package com.vibent.vibentback.user;

import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    UserRepository userRepository;

    public User getUser(String ref) {
        User user = userRepository.findByRef(ref);
        if (user == null)
            throw new VibentException(VibentError.USER_NOT_FOUND);
        return user;
    }

    public User addUser(User user) {
        user.setRef(UUID.randomUUID().toString());
        try {
            User created = userRepository.save(user);
        } catch (Exception e){
            throw new VibentException(VibentError.USER_CANT_CREATE);
        }
        return userRepository.save(user);
    }

    public void deleteUser(String userRef) {
        User user = userRepository.findByRef(userRef);
        user.setDeleted(true);
        userRepository.save(user);
    }

    public User updateUser(String userRef, User newUser) {
        User existing = userRepository.findByRef(userRef);
        ObjectUpdater.updateProperties(existing, newUser);
        return userRepository.save(existing);
    }
}
