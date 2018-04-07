package com.vibent.vibentback.bubble.location;

import com.vibent.vibentback.Mock;
import com.vibent.vibentback.api.location.LocationBubbleUpdateRequest;
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
public class LocationService {

    LocationBubbleRepository bubbleRepository;
    EventRepository eventRepository;
    UserRepository userRepository;

    // Location Bubble -------------------------------------------------------------
    public LocationBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public LocationBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        LocationBubble locationBubble = new LocationBubble();
        locationBubble.setEvent(event);
        locationBubble.setCreator(Mock.getConnectedUser(userRepository));
        locationBubble.setDeleted(false);
        locationBubble.setType(BubbleType.LocationBubble);
        locationBubble = bubbleRepository.save(locationBubble);
        return locationBubble;
    }

    public LocationBubble updateBubble(long id, LocationBubbleUpdateRequest update) {
        LocationBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        ObjectUpdater.updateProperties(update, bubble);
        bubbleRepository.save(bubble);
        return bubble;
    }

    public void deleteBubble(long id) {
        bubbleRepository.deleteById(id);
    }
}
