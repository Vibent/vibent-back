package com.vibent.vibentback.bubble.checkbox;


import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswer;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswerRepository;
import com.vibent.vibentback.bubble.checkbox.option.CheckboxOption;
import com.vibent.vibentback.bubble.checkbox.option.CheckboxOptionRepository;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.api.checkbox.CheckboxAnswerRequest;
import com.vibent.vibentback.api.checkbox.CheckboxBubbleUpdateRequest;
import com.vibent.vibentback.api.checkbox.CheckboxOptionRequest;
import com.vibent.vibentback.api.checkbox.CheckboxOptionUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CheckboxService {

    CheckboxRepository bubbleRepository;
    CheckboxOptionRepository responseRepository;
    CheckboxAnswerRepository checkboxAnswerRepository;
    EventRepository eventRepository;
    ConnectedUserUtils userUtils;

    // Checkbox Bubble -------------------------------------------------------------
    public CheckboxBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public CheckboxBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        CheckboxBubble checkboxBubble = new CheckboxBubble();
        checkboxBubble.setEvent(event);
        checkboxBubble.setCreator(userUtils.getConnectedUser());
        checkboxBubble.setDeleted(false);
        checkboxBubble.setType(BubbleType.SurveyBubble);
        checkboxBubble = bubbleRepository.save(checkboxBubble);
        return checkboxBubble;
    }

    public CheckboxBubble updateBubble(long id, CheckboxBubbleUpdateRequest request) {
        CheckboxBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        bubble.setTitle(request.getTitle());
        bubble = bubbleRepository.save(bubble);
        return bubble;
    }

    public void deleteBubble(long id) {
        bubbleRepository.deleteById(id);
    }

    // Checkbox Bubble Option -------------------------------------------------------------

    public CheckboxBubble createOption(CheckboxOptionRequest request) {
        CheckboxBubble bubble = bubbleRepository.findById(request.getBubbleId())
                .orElseThrow(() -> new VibentException(VibentError.ENTRY_NOT_FOUND));
        CheckboxOption option = new CheckboxOption();
        option.setBubble(bubble);
        option.setContent(request.getContent());
        option.setUser(userUtils.getConnectedUser());
        responseRepository.save(option);
        return bubble;
    }

    public CheckboxBubble updateOption(Long id, CheckboxOptionUpdateRequest request) {
        CheckboxOption option = responseRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.SURVEY_OPTION_NOT_FOUND));
        request.setContent(request.getContent());
        responseRepository.save(option);
        return option.getBubble();
    }

    public void deleteOption(Long id) {
        responseRepository.deleteById(id);
    }

    // Checkbox Bubble Answer -------------------------------------------------------------

    public CheckboxBubble createAnswer(CheckboxAnswerRequest request) {
        CheckboxOption option = responseRepository.findById(request.getCheckboxResponseId())
                .orElseThrow(() -> new VibentException(VibentError.SURVEY_OPTION_NOT_FOUND));
        CheckboxAnswer answer = new CheckboxAnswer();
        answer.setUser(userUtils.getConnectedUser());
        answer.setOption(option);
        checkboxAnswerRepository.save(answer);
        return option.getBubble();
    }

    public void deleteAnswer(Long id) {
        checkboxAnswerRepository.deleteById(id);
    }
}
