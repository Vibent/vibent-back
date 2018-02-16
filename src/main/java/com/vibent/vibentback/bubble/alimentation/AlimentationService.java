package com.vibent.vibentback.bubble.alimentation;

import com.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBringRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import com.vibent.vibentback.bubble.api.AlimentationBubbleRes;
import com.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AlimentationService {

    BubbleOwnershipRepository ownershipRepository;
    AlimentationBubbleRepository bubbleRepository;
    AlimentationEntryRepository entryRepository;
    AlimentationBringRepository bringRepository;

    public AlimentationBubbleRes getAlimentationBubbleResponse(long id){
        AlimentationBubble bubble = bubbleRepository.findById(id);
        if(bubble == null)
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);

        AlimentationBubbleRes response = new AlimentationBubbleRes(bubble);
        ArrayList<AlimentationEntry> entries = entryRepository.findByBubbleId(bubble.getId());
        for(AlimentationEntry entry : entries) {
            ArrayList<AlimentationBring> brings = bringRepository.findByEntryId(entry.getId());
            response.addEntry(entry, brings);
        }
        return response;
    }

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
        AlimentationBubble bubble = bubbleRepository.findById(id);
        if(bubble == null)
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        BubbleOwnership ownership = ownershipRepository.findById(id);
        if(ownership == null)
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        ownership.setDeleted(true);
        ownershipRepository.save(ownership);
    }

    // TODO finish
}
