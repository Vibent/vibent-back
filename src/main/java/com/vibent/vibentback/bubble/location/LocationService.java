package com.vibent.vibentback.bubble.location;

import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LocationBubbleService {

    LocationBubbleRepository locationBubbleRepository;

    public LocationBubble getBubble(long id) {
        LocationBubble locationBubble = locationBubbleRepository.findById(id);
        if (locationBubble == null)
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        return locationBubble;
    }

    public LocationBubble createBubble(String eventRef) {
        return null;
    }

    public void deleteBubble(long id) {
        locationBubbleRepository.deleteById(id);
    }

    public LocationBubble addBubble(LocationBubble locationBubble) {
        try {
            LocationBubble created = locationBubbleRepository.save(locationBubble);
        } catch (Exception e) {
            throw new VibentException(VibentError.BUBBLE_CANT_CREATE);
        }
        return locationBubbleRepository.save(locationBubble);
    }

    public LocationBubble updateBubble(long id, LocationBubble newLocationBubble) {
        LocationBubble existing = locationBubbleRepository.findById(id);
        if (existing == null)
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        ObjectUpdater.updateProperties(existing, newLocationBubble);
        return locationBubbleRepository.save(existing);

    }
}
