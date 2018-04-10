package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBringRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.api.alimentation.AlimentationBringRequest;
import com.vibent.vibentback.api.alimentation.AlimentationBringUpdateRequest;
import com.vibent.vibentback.api.alimentation.AlimentationEntryRequest;
import com.vibent.vibentback.api.alimentation.AlimentationEntryUpdateRequest;
import com.vibent.vibentback.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AlimentationService {

    AlimentationBubbleRepository bubbleRepository;
    AlimentationEntryRepository entryRepository;
    AlimentationBringRepository bringRepository;
    EventRepository eventRepository;
    UserRepository userRepository;
    ConnectedUserUtils userUtils;

    // Alimentation Bubble -------------------------------------------------------------
    @PreAuthorize(value = "hasPermission(#id, 'AlimentationBubble', 'GET')")
    public AlimentationBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public AlimentationBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        AlimentationBubble alimentationBubble = new AlimentationBubble();
        alimentationBubble.setEvent(event);
        alimentationBubble.setCreator(userUtils.getConnectedUser());
        alimentationBubble.setDeleted(false);
        alimentationBubble.setType(BubbleType.AlimentationBubble);
        alimentationBubble = bubbleRepository.save(alimentationBubble);
        return alimentationBubble;
    }

    public void deleteBubble(long id) {
        bubbleRepository.deleteById(id);
    }

    // Alimentation Entry -------------------------------------------------------------
    public AlimentationBubble createEntry(AlimentationEntryRequest request) {
        AlimentationBubble bubble = bubbleRepository.findById(request.getBubbleId())
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        AlimentationEntry entry = new AlimentationEntry();
        entry.setBubble(bubble);
        entry.setName(request.getName());
        entry.setTotalCurrent(0);
        entry.setTotalRequested(request.getTotalRequested());
        entry.setType(request.getType());
        entry.setDeleted(false);
        entry = entryRepository.save(entry);

        bubble.addEntry(entry);
        return entry.getBubble();
    }

    public AlimentationBubble updateEntry(Long id, AlimentationEntryUpdateRequest request) {
        AlimentationEntry entry = entryRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.ENTRY_NOT_FOUND));
        if(request.getName() != null)
            entry.setName(request.getName());
        if(request.getTotalRequested() != null)
            entry.setTotalRequested(request.getTotalRequested());
        if(request.getType() != null){
            entry.setType(request.getType());
        }
        entry = entryRepository.save(entry);
        return entry.getBubble();
    }

    public void deleteEntry(Long id) {
        entryRepository.deleteById(id);
    }

    // Alimentation Bring -------------------------------------------------------------
    public AlimentationBubble createBring(AlimentationBringRequest request) {
        AlimentationEntry entry = entryRepository.findById(request.getEntryId())
                .orElseThrow(() -> new VibentException(VibentError.ENTRY_NOT_FOUND));
        AlimentationBring bring = new AlimentationBring();
        bring.setQuantity(request.getQuantity());
        bring.setUser(userUtils.getConnectedUser());
        bring.setEntry(entry);
        bring.setDeleted(false);
        bringRepository.save(bring);
        return entry.getBubble();
    }

    public AlimentationBubble updateBring(Long id, AlimentationBringUpdateRequest request) {
        if (request.getQuantity() == 0)
            deleteBring(id);
        AlimentationBring bring = bringRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BRING_NOT_FOUND));
        bring.setQuantity(request.getQuantity());
        return bring.getEntry().getBubble();
    }

    public void deleteBring(Long id) {
        bringRepository.deleteById(id);
    }
}
