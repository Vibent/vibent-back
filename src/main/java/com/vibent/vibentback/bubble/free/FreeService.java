package com.vibent.vibentback.bubble.free;

import com.vibent.vibentback.Mock;
import com.vibent.vibentback.api.free.FreeBubbleUpdateRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.common.ObjectUpdater;
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
public class FreeService {

    FreeBubbleRepository bubbleRepository;
    EventRepository eventRepository;
    UserRepository userRepository;

    // Free Bubble -------------------------------------------------------------
    public FreeBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public FreeBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        FreeBubble freeBubble = new FreeBubble();
        freeBubble.setEvent(event);
        freeBubble.setCreator(Mock.getConnectedUser(userRepository));
        freeBubble.setDeleted(false);
        freeBubble.setType(BubbleType.AlimentationBubble);
        freeBubble = bubbleRepository.save(freeBubble);
        return freeBubble;
    }

    public FreeBubble updateBubble(long id, FreeBubbleUpdateRequest update) {
        FreeBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        ObjectUpdater.updateProperties(update, bubble);
        bubbleRepository.save(bubble);
        return bubble;
    }

    public void deleteBubble(long id) {
        bubbleRepository.deleteById(id);
    }

}
