package com.vibent.vibentback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.api.AlimentationBubbleRes;
import com.vibent.vibentback.bubble.free.FreeBubble;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.user.User;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@ActiveProfiles("test")
public class VibentTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    protected static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    protected User RANDOM_USER;
    protected GroupT RANDOM_GROUP;
    protected Event RANDOM_EVENT;

    protected FreeBubble RANDOM_FREE_BUBBLE;

    protected void setUp() {
        RANDOM_USER = new User(UUID.randomUUID().toString(), "test", "test", "test@test.com", "test", "test");
        RANDOM_GROUP = new GroupT(UUID.randomUUID().toString(), "test");
        RANDOM_EVENT = new Event(UUID.randomUUID().toString(), RANDOM_GROUP.getRef(), "test", "test", new Date(), new Date());
        RANDOM_FREE_BUBBLE = new FreeBubble("free", "free");
    }

    protected String getJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}
