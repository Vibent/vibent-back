package com.vibent.vibentback.bubble.location;

import com.vibent.vibentback.user.ConnectedUserUtils;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.bubble.location.api.LocationBubbleUpdateRequest;
import com.vibent.vibentback.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LocationService {

    LocationBubbleRepository bubbleRepository;
    EventRepository eventRepository;
    UserRepository userRepository;
    ConnectedUserUtils userUtils;

    // Location Bubble -------------------------------------------------------------
    public LocationBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public LocationBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        LocationBubble locationBubble = new LocationBubble();
        locationBubble.setEvent(event);
        locationBubble.setCreator(userUtils.getConnectedUser());
        locationBubble.setDeleted(false);
        locationBubble = bubbleRepository.save(locationBubble);
        return locationBubble;
    }

    public LocationBubble updateBubble(long id, LocationBubbleUpdateRequest request) {
        LocationBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        bubble.setCoord(request.getCoord());
        bubbleRepository.save(bubble);
        return bubble;
    }

    public void deleteBubble(long id) {
        bubbleRepository.deleteById(id);
    }
}
