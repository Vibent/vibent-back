package com.vibent.vibentback;

import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.user.User;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.UUID;

@ActiveProfiles("test")
public class VibentTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public User RANDOM_USER = new User(UUID.randomUUID().toString(), "test", "test", "test@test.com", "test", "test");
    public GroupT RANDOM_GROUP = new GroupT(UUID.randomUUID().toString(), "test");
    public Event RANDOM_EVENT = new Event(UUID.randomUUID().toString(), RANDOM_GROUP.getRef(), "test", "test", new Date(), new Date());
}
