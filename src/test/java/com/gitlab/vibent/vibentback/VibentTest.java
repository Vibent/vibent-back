package com.gitlab.vibent.vibentback;

import com.gitlab.vibent.vibentback.user.User;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@ActiveProfiles("test")
public class VibentTest {

    protected final static User RANDOM_USER = new User(UUID.randomUUID().toString(),"conor","ryan","cr.sd.sd@gmail.com","secret","sel");
}
