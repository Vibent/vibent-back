package com.vibent.vibentback.bubble.location;

import com.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LocationBubbleService {

    LocationBubbleRepository locationBubbleRepository;
    BubbleOwnershipRepository ownershipRepository;

    public LocationBubble getBubble(long id) {
        LocationBubble locationBubble = locationBubbleRepository.findById(id);
        if (locationBubble == null)
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        return locationBubble;
    }

    public LocationBubble createBubble(String eventRef){
        LocationBubble locationBubble = locationBubbleRepository.save(new LocationBubble());
        ownershipRepository.save(new BubbleOwnership(eventRef,
                locationBubble.getId(),
                "LocationBubble",
                "CREATOR")); // TODO add creator as connected user
        return locationBubble;
    }

    public void deleteBubble(long id) {
        locationBubbleRepository.deleteById(id);
    }

    public LocationBubble updateBubble(long id, LocationBubble newLocationBubble) {
        LocationBubble existing = locationBubbleRepository.findById(id);
        if(existing == null)
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        ObjectUpdater.updateProperties(existing, newLocationBubble);
        return locationBubbleRepository.save(existing);

    }
}
