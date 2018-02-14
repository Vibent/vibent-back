package com.gitlab.vibent.vibentback;

import com.gitlab.vibent.vibentback.groupT.GroupT;
import com.gitlab.vibent.vibentback.user.User;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.Charset;
import java.util.UUID;

@ActiveProfiles("test")
public class VibentTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public User RANDOM_USER = new User(UUID.randomUUID().toString(),"test","test","test@test.com","test","test");
    public GroupT RANDOM_GROUP = new GroupT(UUID.randomUUID().toString(), "test");
}
