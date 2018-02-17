package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.bubble.alimentation.*;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBringRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import com.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.event.EventRepository;
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

import java.util.Optional;

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
    private AlimentationEntryRepository entryRepository;
    @MockBean
    private AlimentationBringRepository bringRepository;
    @MockBean
    private BubbleOwnershipRepository ownershipRepository;
    @MockBean
    private EventRepository eventRepository;

    AlimentationBubble RANDOM_BUBBLE;
    AlimentationEntry RANDOM_ENTRY;
    AlimentationBring RANDOM_BRING;
    BubbleOwnership RANDOM_OWNERSHIP;
    AlimentationBubbleReq RANDOM_BUBBLE_REQUEST;
    AlimentationEntryReq RANDOM_ENTRY_REQUEST;
    AlimentationEntryUpdateReq RANDOM_ENTRY_UPDATE_REQUEST;
    AlimentationBringReq RANDOM_BRING_REQUEST;
    AlimentationBringUpdateReq RANDOM_BRING_UPDATE_REQUEST;


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

        RANDOM_BUBBLE_REQUEST = new AlimentationBubbleReq();
        RANDOM_BUBBLE_REQUEST.setEventRef(RANDOM_EVENT.getRef());

        RANDOM_ENTRY_REQUEST = new AlimentationEntryReq();
        RANDOM_ENTRY_REQUEST.setBubbleId(RANDOM_BUBBLE.getId());
        RANDOM_ENTRY_REQUEST.setName("Frites");
        RANDOM_ENTRY_REQUEST.setTotalCurrent(0);
        RANDOM_ENTRY_REQUEST.setTotalRequested(10);
        RANDOM_ENTRY_REQUEST.setType(AlimentationEntry.Type.Food);

        RANDOM_ENTRY_UPDATE_REQUEST = new AlimentationEntryUpdateReq();
        RANDOM_ENTRY_UPDATE_REQUEST.setTotalRequested(15);

        RANDOM_BRING_REQUEST = new AlimentationBringReq();
        RANDOM_BRING_REQUEST.setEntryId(RANDOM_ENTRY.getId());
        RANDOM_BRING_REQUEST.setQuantity(10);

        RANDOM_BRING_UPDATE_REQUEST = new AlimentationBringUpdateReq();
        RANDOM_BRING_UPDATE_REQUEST.setQuantity(15);

        when(bubbleRepository.findById(RANDOM_BUBBLE.getId())).thenReturn(Optional.of(RANDOM_BUBBLE));
        when(bubbleRepository.existsById(RANDOM_BUBBLE.getId())).thenReturn(true);
        when(bubbleRepository.save(new AlimentationBubble())).thenReturn(RANDOM_BUBBLE);
        when(eventRepository.findByRef(RANDOM_EVENT.getRef())).thenReturn(Optional.of(RANDOM_EVENT));
        when(ownershipRepository.findByIdAndBubbleType(RANDOM_BUBBLE.getId(), BubbleType.AlimentationBubble))
                .thenReturn(Optional.of(RANDOM_OWNERSHIP));
        when(entryRepository.findById(RANDOM_ENTRY.getId())).thenReturn(Optional.of(RANDOM_ENTRY));
        when(entryRepository.save(RANDOM_ENTRY)).thenReturn(RANDOM_ENTRY);
        when(entryRepository.existsById(RANDOM_ENTRY.getId())).thenReturn(true);
        when(bringRepository.findById(RANDOM_BRING.getId())).thenReturn(Optional.of(RANDOM_BRING));
        when(bringRepository.save(RANDOM_BRING)).thenReturn(RANDOM_BRING);
        when(bringRepository.existsById(RANDOM_BRING.getId())).thenReturn(true);
        when(bringRepository.getBubbleId(RANDOM_BRING.getId())).thenReturn(RANDOM_BUBBLE.getId());
    }

    @Test
    public void getBubble() {
        AlimentationBubbleRes bubble = controller.getBubble(RANDOM_BUBBLE.getId());
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
        AlimentationBubbleRes bubble = controller.createBubble(RANDOM_BUBBLE_REQUEST);
        Assert.assertEquals(bubble.getId(), bubble.getId());
    }

    @Test
    public void createBubbleWithWrongEventRef() {
        exception.expect(VibentException.class);
        exception.expectMessage("event-not-found");

        AlimentationBubbleRes bubble = controller.createBubble(new AlimentationBubbleReq());
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

    // entry
    @Test
    public void createBubbleEntry(){
        AlimentationBubbleRes res = controller.createBubbleEntry(RANDOM_ENTRY_REQUEST);
        Assert.assertEquals(res.getId(), RANDOM_BUBBLE.getId());
    }

    @Test
    public void updateBubbleEntry(){
        AlimentationBubbleRes res = controller.updateBubbleEntry(RANDOM_ENTRY.getId(), RANDOM_ENTRY_UPDATE_REQUEST);
        Assert.assertNotNull(res);
    }

    @Test
    public void updateBubbleEntryWringId(){
        exception.expect(VibentException.class);
        exception.expectMessage("entry-not-found");

        controller.updateBubbleEntry(-1L, RANDOM_ENTRY_UPDATE_REQUEST);
    }

    @Test
    public void deleteEntry(){
        controller.deleteBubbleEntry(RANDOM_ENTRY.getId());
    }

    @Test
    public void deleteEntryWrongId(){
        exception.expect(VibentException.class);
        exception.expectMessage("entry-not-found");

        controller.deleteBubbleEntry(-1L);
    }

    // Bring
    @Test
    public void createBubbleBring(){
        AlimentationBubbleRes res = controller.createBubbleBring(RANDOM_BRING_REQUEST);
        Assert.assertEquals(res.getId(), RANDOM_BUBBLE.getId());
    }

    @Test
    public void updateBubbleBring(){
        AlimentationBubbleRes res = controller.updateBubbleBring(RANDOM_BRING.getId(), RANDOM_BRING_UPDATE_REQUEST);
        Assert.assertNotNull(res);
    }

    @Test
    public void updateBubbleBringWringId(){
        exception.expect(VibentException.class);
        exception.expectMessage("bring-not-found");

        controller.updateBubbleBring(-1L, RANDOM_BRING_UPDATE_REQUEST);
    }

    @Test
    public void deleteBring(){
        controller.deleteBubbleBring(RANDOM_BRING.getId());
    }

    @Test
    public void deleteBringWrongId(){
        exception.expect(VibentException.class);
        exception.expectMessage("bring-not-found");

        controller.deleteBubbleBring(-1L);
    }

}
