package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.user.ConnectedUserUtils;
import com.vibent.vibentback.api.bubble.alimentation.*;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBringRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        if (request.getName() != null)
            entry.setName(request.getName());
        if (request.getTotalRequested() != null)
            entry.setTotalRequested(request.getTotalRequested());
        if (request.getType() != null) {
            entry.setType(request.getType());
        }
        entry = entryRepository.save(entry);
        return entry.getBubble();
    }

    public void deleteEntry(Long id) {
        AlimentationEntry entry = entryRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.ENTRY_NOT_FOUND));
        AlimentationBubble bubble = entry.getBubble();
        bubble.getEntries().remove(entry);
        entryRepository.deleteById(id);
        bubbleRepository.save(bubble);
    }

    // Alimentation Bring -------------------------------------------------------------
    public AlimentationBubble changeBring(AlimentationBringChangeRequest request) {
        AlimentationBring bring;
        AlimentationEntry entry = entryRepository.findById(request.getEntryId())
                .orElseThrow(() -> new VibentException(VibentError.ENTRY_NOT_FOUND));
        Optional<AlimentationBring> bringOpt = entry.getBrings().stream().filter(
                b -> b.getUserRef().equals(userUtils.getConnectedUserRef())).findFirst();

        // Bring already exists for connected user
        if (bringOpt.isPresent()) {
            bring = bringOpt.get();
            bring.setQuantity(bring.getQuantity() + request.getQuantity());
            if (bring.getQuantity() < 1L) {
                this.deleteBring(bring.getId());
            } else {
                bringRepository.save(bring);
            }
            return entry.getBubble();
        }

        // Bring doesn't already exist, need to create
        else {
            if (request.getQuantity() < 1L) {
                return entry.getBubble();
            }
            AlimentationBringRequest newRequest = new AlimentationBringRequest();
            newRequest.setQuantity(request.getQuantity());
            newRequest.setEntryId(request.getEntryId());
            return this.createBring(newRequest);
        }
    }

    public AlimentationBubble createBring(AlimentationBringRequest request) {
        AlimentationEntry entry = entryRepository.findById(request.getEntryId())
                .orElseThrow(() -> new VibentException(VibentError.ENTRY_NOT_FOUND));

        if (entry.getBrings().stream().anyMatch(b -> b.getUserRef().equals(userUtils.getConnectedUserRef()))) {
            throw new VibentException(VibentError.BRING_ALREADY_EXISTS);
        }

        AlimentationBring bring = new AlimentationBring();
        bring.setQuantity(request.getQuantity());
        bring.setUser(userUtils.getConnectedUser());
        bring.setEntry(entry);
        bring.setDeleted(false);
        bringRepository.save(bring);

        entry.addBring(bring);
        return entry.getBubble();
    }

    public AlimentationBubble updateBring(Long id, AlimentationBringUpdateRequest request) {
        if (request.getQuantity() == 0)
            deleteBring(id);
        AlimentationBring bring = bringRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BRING_NOT_FOUND));
        bring.setQuantity(request.getQuantity());
        bringRepository.save(bring);
        return bring.getEntry().getBubble();
    }

    public void deleteBring(Long id) {
        AlimentationBring bring = bringRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BRING_NOT_FOUND));
        AlimentationEntry entry = bring.getEntry();
        entry.getBrings().remove(bring);
        bringRepository.deleteById(id);
        entryRepository.save(entry);
    }
}
