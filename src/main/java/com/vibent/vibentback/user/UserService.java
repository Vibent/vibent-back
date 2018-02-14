package com.vibent.vibentback.user;

import com.vibent.vibentback.common.ObjectUpdater;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    UserRepository userRepository;

    public User getUser(String ref) {
        return userRepository.findByRef(ref);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String userRef) {
        User user = userRepository.findByRef(userRef);
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    public User updateUser(String userRef, User newUser) {
        User existing = userRepository.findByRef(userRef);
        ObjectUpdater.updateProperties(existing, newUser);
        return userRepository.save(existing);
    }
}
