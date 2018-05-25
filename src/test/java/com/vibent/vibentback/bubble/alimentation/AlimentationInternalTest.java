package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.alimentation.AlimentationBubbleRequest;
import com.vibent.vibentback.api.event.EventRequest;
import com.vibent.vibentback.api.event.EventUpdateRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventController;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.eventParticipation.EventParticipation;
import com.vibent.vibentback.eventParticipation.EventParticipationRepository;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.user.UserRepository;
import com.vibent.vibentback.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AlimentationInternalTest extends VibentTest {
    @Test
    public void mockTest() {
        // TODO add tests
    }
}
