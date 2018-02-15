package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBringRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import com.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.user.User;
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

    public AlimentationBubble getBubble(long id){
        AlimentationBubble bubble = bubbleRepository.findById(id);
        if(bubble == null)
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        return bubble;
    }

    public AlimentationBubble createBubble(String eventRef){
        AlimentationBubble alimentationBubble = bubbleRepository.save(new AlimentationBubble());
        ownershipRepository.save(new BubbleOwnership(eventRef,
                alimentationBubble.getId(),
                BubbleOwnership.Type.AlimentationBubble,
                "CREATOR")); // TODO add creator as connected user
        return alimentationBubble;
    }

    public AlimentationBubble updateBubble(long id, AlimentationBubble bubble){
        AlimentationBubble old = bubbleRepository.findById(id);
        if(bubble == null)
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        ObjectUpdater.updateProperties(bubble, old);
        return bubbleRepository.save(old);
    }

    public void deleteBubble(long id){
        bubbleRepository.deleteById(id);
    }

    // TODO finish
}
