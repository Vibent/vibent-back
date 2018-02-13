package com.gitlab.vibent.vibentback.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    UserRepository userRepository;

    public User getUser(String ref){
        User user = userRepository.findByRef(ref);
        return user;
    }

    public User addUser(User user){
        return userRepository.save(user);
    }
}
