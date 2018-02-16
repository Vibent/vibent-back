package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.AlimentationBubbleRes;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserController;
import com.vibent.vibentback.user.UserRepository;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class AlimentationInternalTest extends VibentTest {

    @Autowired
    @InjectMocks
    private AlimentationController controller;

    @MockBean
    private AlimentationBubbleRepository bubbleRepository;
    @MockBean
    private BubbleOwnershipRepository ownershipRepository;
    @MockBean
    private EventRepository eventRepository;

    AlimentationBubble RANDOM_BUBBLE;
    AlimentationEntry RANDOM_ENTRY;
    AlimentationBring RANDOM_BRING;
    BubbleOwnership RANDOM_OWNERSHIP;


    @Before
    public void setUp() {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        RANDOM_BUBBLE = new AlimentationBubble();
        RANDOM_BUBBLE.setId(666L);

        RANDOM_ENTRY = new AlimentationEntry();
        RANDOM_ENTRY.setId(123L);
        RANDOM_ENTRY.setBubbleId(RANDOM_BUBBLE.getId());
        RANDOM_ENTRY.setDeleted(false);
        RANDOM_ENTRY.setName("Coke");
        RANDOM_ENTRY.setType(AlimentationEntry.Type.Drink);
        RANDOM_ENTRY.setTotalCurrent(5);
        RANDOM_ENTRY.setTotalRequested(10);

        RANDOM_BRING = new AlimentationBring();
        RANDOM_BRING.setId(546L);
        RANDOM_BRING.setEntryId(RANDOM_ENTRY.getId());
        RANDOM_BRING.setDeleted(false);
        RANDOM_BRING.setQuantity(5);
        RANDOM_BRING.setUserRef(RANDOM_USER.getRef());

        RANDOM_OWNERSHIP = new BubbleOwnership();
        RANDOM_OWNERSHIP.setId(789L);
        RANDOM_OWNERSHIP.setDeleted(false);
        RANDOM_OWNERSHIP.setCreatorRef(RANDOM_USER.getRef());
        RANDOM_OWNERSHIP.setBubbleId(RANDOM_BUBBLE.getId());
        RANDOM_OWNERSHIP.setEventRef(RANDOM_EVENT.getRef());
        RANDOM_OWNERSHIP.setBubbleType(BubbleType.AlimentationBubble);

        when(bubbleRepository.findById(RANDOM_BUBBLE.getId().longValue())).thenReturn(RANDOM_BUBBLE);
        when(bubbleRepository.save(new AlimentationBubble())).thenReturn(RANDOM_BUBBLE);
        when(eventRepository.findByRef(RANDOM_EVENT.getRef())).thenReturn(RANDOM_EVENT);
        when(ownershipRepository.findByIdAndBubbleType(RANDOM_BUBBLE.getId(), BubbleType.AlimentationBubble))
                .thenReturn(RANDOM_OWNERSHIP);
    }

    @Test
    public void getBubble() {
        AlimentationBubble bubble = controller.getBubble(RANDOM_BUBBLE.getId());
        Assert.assertEquals(RANDOM_BUBBLE.getId(), bubble.getId());
    }

    @Test
    public void getNonExistingBubble() throws VibentException {
        exception.expect(VibentException.class);
        exception.expectMessage("bubble-not-found");

        controller.getBubble(-1L);
    }

    @Test
    public void createBubble() {
        AlimentationBubble bubble = controller.createBubble(RANDOM_EVENT.getRef());
        Assert.assertEquals(bubble.getId(), bubble.getId());
    }

    @Test
    public void createBubbleWithWrongEventRef() {
        exception.expect(VibentException.class);
        exception.expectMessage("event-not-found");

        AlimentationBubble bubble = controller.createBubble("wrong event ref");
    }

    @Test
    public void deleteBubble() {
        controller.deleteBubble(RANDOM_BUBBLE.getId());
    }

    @Test
    public void deleteNonExistingBubble() {
        exception.expect(VibentException.class);
        exception.expectMessage("bubble-not-found");

        controller.deleteBubble(-1L);
    }
}
