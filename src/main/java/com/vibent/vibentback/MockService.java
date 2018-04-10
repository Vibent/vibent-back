package com.vibent.vibentback;

import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MockService {

    private UserRepository userRepository;

    public void sendConfirmationMail(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }
}
