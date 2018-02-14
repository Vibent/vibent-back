package com.gitlab.vibent.vibentback.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    UserRepository userRepository;

    public User getUser(String ref){
        return userRepository.findByRef(ref);
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(String userRef){
        User user = userRepository.findByRef(userRef);
        user.setDeleted(true);
        userRepository.save(user);
    }

    public User updateUser(String userRef, User user){
        User existing = userRepository.findByRef(userRef);
        user.setId(existing.getId());
        return userRepository.save(user);
    }
}
