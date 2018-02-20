package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.Mock;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBringRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import com.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.user.UserRepository;
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
    UserRepository userRepository;

    // Alimentation Bubble -------------------------------------------------------------
    public AlimentationBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public AlimentationBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        AlimentationBubble alimentationBubble = new AlimentationBubble();
        alimentationBubble.setEvent(event);
        alimentationBubble.setCreator(Mock.getConnectedUser(userRepository));
        alimentationBubble.setDeleted(false);
        alimentationBubble.setType(BubbleType.AlimentationBubble);
        alimentationBubble = bubbleRepository.save(alimentationBubble);
        return alimentationBubble;
    }

    public void deleteBubble(long id) {
        AlimentationBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        bubbleRepository.deleteById(bubble.getId());
    }
}
