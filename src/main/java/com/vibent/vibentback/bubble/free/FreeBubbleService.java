package com.vibent.vibentback.bubble.free;

import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FreeBubbleService {

    FreeBubbleRepository freeBubbleRepository;
    BubbleOwnershipRepository ownershipRepository;

    public FreeBubble getBubble(long id) {
        FreeBubble freeBubble = freeBubbleRepository.findById(id);
        if (freeBubble == null)
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        return freeBubble;
    }

    public FreeBubble createBubble(String eventRef) {
        FreeBubble freeBubble = freeBubbleRepository.save(new FreeBubble());
        ownershipRepository.save(new BubbleOwnership(eventRef,
                freeBubble.getId(),
                BubbleType.FreeBubble,
                "CREATOR")); // TODO add creator as connected user
        return freeBubble;
    }

    public void deleteBubble(long id) {
        freeBubbleRepository.deleteById(id);
    }

    public FreeBubble addBubble(FreeBubble freeBubble) {
        try {
            FreeBubble created = freeBubbleRepository.save(freeBubble);
        } catch (Exception e) {
            throw new VibentException(VibentError.BUBBLE_CANT_CREATE);
        }
        return freeBubbleRepository.save(freeBubble);
    }

    public FreeBubble updateBubble(long id, FreeBubble newFreeBubble) {
        FreeBubble existing = freeBubbleRepository.findById(id);
        if (existing == null)
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        ObjectUpdater.updateProperties(existing, newFreeBubble);
        return freeBubbleRepository.save(existing);

    }
}
