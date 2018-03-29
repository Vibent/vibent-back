package com.vibent.vibentback.bubble.checkbox;


import com.vibent.vibentback.Mock;
import com.vibent.vibentback.api.checkbox.CheckboxBubbleUpdateRequest;
import com.vibent.vibentback.api.checkbox.CheckboxResponseRequest;
import com.vibent.vibentback.api.checkbox.CheckboxResponseUpdateRequest;
import com.vibent.vibentback.api.checkbox.UsersCheckboxResponsesRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.checkbox.entry.CheckboxResponse;
import com.vibent.vibentback.bubble.checkbox.entry.CheckboxResponseRepository;
import com.vibent.vibentback.bubble.checkbox.usersResponses.UsersCheckboxResponses;
import com.vibent.vibentback.bubble.checkbox.usersResponses.UsersCheckboxResponsesRepository;
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
public class CheckboxService {

    CheckboxRepository bubbleRepository;
    CheckboxResponseRepository responseRepository;
    UsersCheckboxResponsesRepository usersCheckboxResponsesRepository;
    EventRepository eventRepository;
    UserRepository userRepository;

    // Checkbox Bubble -------------------------------------------------------------
    public CheckboxBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public CheckboxBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        CheckboxBubble checkboxBubble = new CheckboxBubble();
        checkboxBubble.setEvent(event);
        checkboxBubble.setCreator(Mock.getConnectedUser(userRepository));
        checkboxBubble.setDeleted(false);
        checkboxBubble.setType(BubbleType.SurveyBubble);
        checkboxBubble = bubbleRepository.save(checkboxBubble);
        return checkboxBubble;
    }

    public CheckboxBubble updateBubble(long id, CheckboxBubbleUpdateRequest update) {
        CheckboxBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        ObjectUpdater.updateProperties(update, bubble);
        bubbleRepository.save(bubble);
        return bubble;
    }

    public void deleteBubble(long id) {
        bubbleRepository.deleteById(id);
    }

    // Checkbox Bubble Response -------------------------------------------------------------

    public CheckboxBubble createResponse(CheckboxResponseRequest request) {
        CheckboxBubble bubble = bubbleRepository.findById(request.getBubbleId())
                .orElseThrow(() -> new VibentException(VibentError.ENTRY_NOT_FOUND));
        CheckboxResponse response = new CheckboxResponse();
        response.setBubble(bubble);
        response.setContent(request.getContent());
        response.setDeleted(false);
        responseRepository.save(response);
        return bubble;
    }

    public CheckboxBubble updateResponse(Long id, CheckboxResponseUpdateRequest request) {
        CheckboxResponse response = responseRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.ANSWER_NOT_FOUND));
        ObjectUpdater.updateProperties(request, response);
        responseRepository.save(response);
        return response.getBubble();
    }

    public void deleteResponse(Long id) {
        responseRepository.deleteById(id);
    }

    // Checkbox Bubble user response -------------------------------------------------------------

    public CheckboxBubble createUserResponse(UsersCheckboxResponsesRequest request) {
        CheckboxResponse response = responseRepository.findById(request.getCheckboxResponseId())
                .orElseThrow(() -> new VibentException(VibentError.ANSWER_NOT_FOUND));
        UsersCheckboxResponses usersCheckboxResponses = new UsersCheckboxResponses();
        usersCheckboxResponses.setUser(Mock.getConnectedUser(userRepository));
        usersCheckboxResponses.setResponse(response);
        usersCheckboxResponses.setDeleted(false);
        usersCheckboxResponsesRepository.save(usersCheckboxResponses);
        return response.getBubble();
    }

    public void deleteUserResponse(Long id) {
        usersCheckboxResponsesRepository.deleteById(id);
    }
}
