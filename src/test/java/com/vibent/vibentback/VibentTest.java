package com.vibent.vibentback;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vibent.vibentback.auth.VibentAuthentication;
import com.vibent.vibentback.distributionlist.DistributionList;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.distributionlist.membership.DistributionListMembership;
import com.vibent.vibentback.user.User;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

public class VibentTest {

    @Value("${vibent.auth.secret")
    private String secret;

    @Autowired
    protected Environment env;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    protected static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    protected User RANDOM_USER;
    protected Event RANDOM_EVENT;
    protected DistributionList RANDOM_DISTRIBUTION_LIST;
    protected Authentication AUTHENTICATION;

    protected void setUp() {

        RANDOM_USER = new User();
        RANDOM_USER.setRef(UUID.randomUUID().toString());
        RANDOM_USER.setFirstName("firstName");
        RANDOM_USER.setLastName("lastName");
        RANDOM_USER.setPassword("$2a$10$cLAIXc2UWiVdSGjxI3Fr5uJUvinj5hBHW1ySIW02.yjrS0DaAvs1O");
        RANDOM_USER.setEmail("vibentJUnit@vibent.com");

        AUTHENTICATION = new VibentAuthentication(
                RANDOM_USER.getRef(),
                null
        );
        SecurityContextHolder.getContext().setAuthentication(AUTHENTICATION);

        RANDOM_EVENT = new Event();
        RANDOM_EVENT.setRef(UUID.randomUUID().toString());
        RANDOM_EVENT.setCreator(RANDOM_USER);
        RANDOM_EVENT.setTitle("test");
        RANDOM_EVENT.setDescription("test");
        RANDOM_EVENT.setStartDate(getFutureDate(5));

        RANDOM_DISTRIBUTION_LIST = new DistributionList();
        RANDOM_DISTRIBUTION_LIST.setId(5L);
        RANDOM_DISTRIBUTION_LIST.setTitle("Distribution list");
        RANDOM_DISTRIBUTION_LIST.setDescription("Descr");
        RANDOM_DISTRIBUTION_LIST.setCreator(RANDOM_USER);
        DistributionListMembership membership = new DistributionListMembership();
        membership.setUser(RANDOM_USER);
        membership.setDistributionList(RANDOM_DISTRIBUTION_LIST);
        RANDOM_DISTRIBUTION_LIST.setMemberships(Collections.singleton(membership));
    }

    protected String getJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }


    protected static Date getFutureDate(int days) {
        Date currentDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    protected static Date getPastDate(int days) {
        return getFutureDate(-days);
    }


}
