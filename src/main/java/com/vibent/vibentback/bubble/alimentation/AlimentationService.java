package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.api.bubble.alimentation.*;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBringRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import com.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AlimentationService {

    BubbleOwnershipRepository ownershipRepository;
    AlimentationBubbleRepository bubbleRepository;
    AlimentationEntryRepository entryRepository;
    AlimentationBringRepository bringRepository;
    EventRepository eventRepository;

    // Alimentation Bubble -------------------------------------------------------------
    public AlimentationBubbleRes getBubbleResponse(long id) {
        AlimentationBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        return getBubbleResponse(bubble);
    }

    public AlimentationBubbleRes getBubbleResponse(AlimentationBubble bubble) {
        AlimentationBubbleRes response = new AlimentationBubbleRes(bubble);
        Iterable<AlimentationEntry> entries = entryRepository.findByBubbleId(bubble.getId());
        for (AlimentationEntry entry : entries) {
            Iterable<AlimentationBring> brings = bringRepository.findByEntryId(entry.getId());
            response.addEntry(entry, brings);
        }
        return response;
    }

    public AlimentationBubbleRes getBubble(long id) {
        return getBubbleResponse(id);
    }

    public AlimentationBubbleRes createBubble(AlimentationBubbleReq request) {
        Event event = eventRepository.findByRef(request.getEventRef())
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        AlimentationBubble alimentationBubble = bubbleRepository.save(new AlimentationBubble());
        ownershipRepository.save(new BubbleOwnership(event.getRef(),
                alimentationBubble.getId(),
                BubbleType.AlimentationBubble,
                "CREATOR")); // TODO add creator as connected user
        return getBubbleResponse(alimentationBubble);
    }

    public AlimentationBubbleRes updateBubble(long id, AlimentationBubble bubble) {
        AlimentationBubble old = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        ObjectUpdater.updateProperties(bubble, old);
        bubble = bubbleRepository.save(old);
        return getBubbleResponse(bubble);
    }

    public void deleteBubble(long id) {
        AlimentationBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        BubbleOwnership ownership = ownershipRepository.findByIdAndBubbleType(id, BubbleType.AlimentationBubble)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        ownershipRepository.deleteById(ownership.getId());
    }

    // Alimentation Bubble Entry -------------------------------------------------------------

    public AlimentationBubbleRes createBubbleEntry(AlimentationEntryReq request) {
        if( ! bubbleRepository.existsById(request.getBubbleId()))
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        AlimentationEntry entry = new AlimentationEntry();
        ObjectUpdater.updateProperties(request, entry);
        entryRepository.save(entry);
        return getBubbleResponse(request.getBubbleId());
    }

    public AlimentationBubbleRes updateBubbleEntry(Long id, AlimentationEntryUpdateReq update) {
        AlimentationEntry entry = entryRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.ENTRY_NOT_FOUND));
        ObjectUpdater.updateProperties(update, entry);
        entryRepository.save(entry);
        return getBubbleResponse(entry.getBubbleId());
    }

    public void deleteBubbleEntry(Long id) {
        if(! entryRepository.existsById(id))
            throw new VibentException(VibentError.ENTRY_NOT_FOUND);
        entryRepository.deleteById(id);
    }

    // Alimentation Bubble Entry Bring -------------------------------------------------------------

    public AlimentationBubbleRes createBubbleBring(AlimentationBringReq request) {
        AlimentationEntry entry = entryRepository.findById(request.getEntryId())
            .orElseThrow(() -> new VibentException(VibentError.ENTRY_NOT_FOUND));
        AlimentationBring bring = new AlimentationBring();
        ObjectUpdater.updateProperties(request, bring);
        bring.setUserRef("CREATOR"); // TODO replace with connected user
        bringRepository.save(bring);
        return getBubbleResponse(entry.getBubbleId());
    }

    public AlimentationBubbleRes updateBubbleBring(Long id, AlimentationBringUpdateReq update) {
        AlimentationBring bring = bringRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BRING_NOT_FOUND));
        bring.setQuantity(update.getQuantity());
        bringRepository.save(bring);
        return getBubbleResponse(bringRepository.getBubbleId(bring.getId()));
    }

    public void deleteBubbleBring(Long id) {
        if(! bringRepository.existsById(id))
            throw new VibentException(VibentError.BRING_NOT_FOUND);
        bringRepository.deleteById(id);
    }
}
