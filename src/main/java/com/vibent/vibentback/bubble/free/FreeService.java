package com.vibent.vibentback.bubble.free;

import com.vibent.vibentback.user.ConnectedUserUtils;
import com.vibent.vibentback.bubble.free.api.FreeBubbleRequest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.bubble.free.api.FreeBubbleUpdateRequest;
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
    ConnectedUserUtils userUtils;

    // Free Bubble -------------------------------------------------------------
    public FreeBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public FreeBubble createBubble(FreeBubbleRequest request) {
        Event event = eventRepository.findByRef(request.getEventRef())
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        FreeBubble freeBubble = new FreeBubble();
        freeBubble.setEvent(event);
        freeBubble.setTitle(request.getTitle());
        freeBubble.setContent(request.getContent());
        freeBubble.setCreator(userUtils.getConnectedUser());
        freeBubble.setDeleted(false);
        freeBubble = bubbleRepository.save(freeBubble);
        return freeBubble;
    }

    public FreeBubble updateBubble(long id, FreeBubbleUpdateRequest request) {
        FreeBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        if(request.getTitle() != null)
            bubble.setTitle(request.getTitle());
        if(request.getContent() != null)
            bubble.setContent(request.getContent());
        bubble = bubbleRepository.save(bubble);
        return bubble;
    }

    public void deleteBubble(long id) {
        bubbleRepository.deleteById(id);
    }

}
