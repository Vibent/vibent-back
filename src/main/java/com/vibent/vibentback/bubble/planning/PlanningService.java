package com.vibent.vibentback.bubble.planning;

import com.vibent.vibentback.Mock;
import com.vibent.vibentback.api.planning.PlanningBubbleUpdateRequest;
import com.vibent.vibentback.api.planning.PlanningEntryRequest;
import com.vibent.vibentback.api.planning.PlanningEntryUpdateRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntry;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntryRepository;
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
public class PlanningService {

    PlanningBubbleRepository bubbleRepository;
    PlanningEntryRepository entryRepository;
    EventRepository eventRepository;
    UserRepository userRepository;

    // Planning Bubble -------------------------------------------------------------
    public PlanningBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public PlanningBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        PlanningBubble planningBubble = new PlanningBubble();
        planningBubble.setEvent(event);
        planningBubble.setCreator(Mock.getConnectedUser(userRepository));
        planningBubble.setDeleted(false);
        planningBubble.setType(BubbleType.PlanningBubble);
        planningBubble = bubbleRepository.save(planningBubble);
        return planningBubble;
    }

    public PlanningBubble updateBubble(long id, PlanningBubbleUpdateRequest update) {
        PlanningBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        ObjectUpdater.updateProperties(update, bubble);
        bubbleRepository.save(bubble);
        return bubble;
    }

    public void deleteBubble(long id) {
        bubbleRepository.deleteById(id);
    }

    // Planning Bubble Entry -------------------------------------------------------------

    public PlanningBubble createEntry(PlanningEntryRequest request) {
        PlanningBubble bubble = bubbleRepository.findById(request.getBubbleId())
                .orElseThrow(() -> new VibentException(VibentError.ENTRY_NOT_FOUND));
        PlanningEntry entry = new PlanningEntry();
        entry.setBubble(bubble);
        entry.setContent(request.getContent());
        entry.setDeleted(false);
        entryRepository.save(entry);
        return bubble;
    }

    public PlanningBubble updateEntry(Long id, PlanningEntryUpdateRequest request) {
        PlanningEntry entry = entryRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.ANSWER_NOT_FOUND));
        ObjectUpdater.updateProperties(request, entry);
        entryRepository.save(entry);
        return entry.getBubble();
    }

    public void deleteEntry(Long id) {
        entryRepository.deleteById(id);
    }

}
