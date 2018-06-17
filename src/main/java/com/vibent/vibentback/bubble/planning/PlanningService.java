package com.vibent.vibentback.bubble.planning;

import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntry;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntryRepository;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.api.bubble.planning.PlanningBubbleUpdateRequest;
import com.vibent.vibentback.api.bubble.planning.PlanningEntryRequest;
import com.vibent.vibentback.api.bubble.planning.PlanningEntryUpdateRequest;
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
    ConnectedUserUtils userUtils;

    // Planning Bubble -------------------------------------------------------------
    public PlanningBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public PlanningBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        PlanningBubble planningBubble = new PlanningBubble();
        planningBubble.setEvent(event);
        planningBubble.setCreator(userUtils.getConnectedUser());
        planningBubble.setDeleted(false);
        planningBubble.setType(BubbleType.PlanningBubble);
        planningBubble = bubbleRepository.save(planningBubble);
        return planningBubble;
    }

    public PlanningBubble updateBubble(long id, PlanningBubbleUpdateRequest update) {
        PlanningBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        bubble.setTitle(update.getTitle());
        bubble = bubbleRepository.save(bubble);
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
                .orElseThrow(() -> new VibentException(VibentError.SURVEY_OPTION_NOT_FOUND));
        if(request.getContent() != null)
            entry.setContent(request.getContent());
        if(request.getStart() != null)
            entry.setStart(request.getStart());
        if(request.getEnd() != null)
            entry.setEnd(request.getEnd());
        entry = entryRepository.save(entry);
        return entry.getBubble();
    }

    public void deleteEntry(Long id) {
        entryRepository.deleteById(id);
    }

}
