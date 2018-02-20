package com.vibent.vibentback;

import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;

import java.util.UUID;

public class Mock {

    public static User getConnectedUser(UserRepository repository){
        User user = new User(UUID.randomUUID().toString(), "test", "test", "test@test.com", "test", "test");
        return repository.save(user);
    }
}
