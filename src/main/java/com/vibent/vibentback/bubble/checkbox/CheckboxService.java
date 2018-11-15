package com.vibent.vibentback.bubble.checkbox;


import com.vibent.vibentback.user.ConnectedUserUtils;
import com.vibent.vibentback.api.bubble.checkbox.*;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswer;
import com.vibent.vibentback.bubble.checkbox.answer.CheckboxAnswerRepository;
import com.vibent.vibentback.bubble.checkbox.option.CheckboxOption;
import com.vibent.vibentback.bubble.checkbox.option.CheckboxOptionRepository;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CheckboxService {

    CheckboxBubbleRepository bubbleRepository;
    CheckboxOptionRepository optionRepository;
    CheckboxAnswerRepository answerRepository;
    EventRepository eventRepository;
    ConnectedUserUtils userUtils;

    // Checkbox Bubble -------------------------------------------------------------
    public CheckboxBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public CheckboxBubble createBubble(CheckboxBubbleRequest request) {
        Event event = eventRepository.findByRef(request.getEventRef())
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        CheckboxBubble checkboxBubble = new CheckboxBubble();
        checkboxBubble.setEvent(event);
        checkboxBubble.setTitle(request.getTitle());
        checkboxBubble.setCreator(userUtils.getConnectedUser());
        checkboxBubble.setDeleted(false);
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
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        CheckboxOption option = new CheckboxOption();
        option.setBubble(bubble);
        option.setContent(request.getContent());
        option.setUser(userUtils.getConnectedUser());
        bubble.getOptions().add(option);
        return bubbleRepository.save(bubble);
    }

    public CheckboxBubble updateOption(Long id, CheckboxOptionUpdateRequest request) {
        CheckboxOption option = optionRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.OPTION_NOT_FOUND));
        option.setContent(request.getContent());
        optionRepository.save(option);
        return bubbleRepository.findById(option.getBubble().getId())
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public void deleteOption(Long id) {
        CheckboxOption option = optionRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.OPTION_NOT_FOUND));
        CheckboxBubble bubble = option.getBubble();
        bubble.getOptions().remove(option);
        optionRepository.delete(option);
        bubbleRepository.save(bubble);
    }

    // Checkbox Bubble Answer -------------------------------------------------------------

    public CheckboxBubble createAnswer(CheckboxAnswerRequest request) {
        CheckboxOption option = optionRepository.findById(request.getOptionId())
                .orElseThrow(() -> new VibentException(VibentError.OPTION_NOT_FOUND));
        if (option.getAnswers().stream().anyMatch(a -> a.getUserRef().equals(userUtils.getConnectedUserRef()))) {
            throw new VibentException(VibentError.ANSWER_ALREADY_CREATED);
        }
        CheckboxAnswer answer = new CheckboxAnswer();
        answer.setUser(userUtils.getConnectedUser());
        answer.setOption(option);
        option.getAnswers().add(answer);
        optionRepository.save(option);
        return option.getBubble();
    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
        CheckboxAnswer answer = answerRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.ANSWER_NOT_FOUND));
        CheckboxOption option = answer.getOption();
        option.getAnswers().remove(answer);
        answerRepository.delete(answer);
        optionRepository.save(option);
    }

    public void deleteAnswerOfOption(Long id) {
        CheckboxOption option = optionRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.OPTION_NOT_FOUND));
        Optional<CheckboxAnswer> answerOptional = option.getAnswers().stream().filter(
                a -> a.getUserRef().equals(userUtils.getConnectedUserRef())).findFirst();
        if (!answerOptional.isPresent()) {
            throw new VibentException(VibentError.ANSWER_NOT_FOUND);
        }
        this.deleteAnswer(answerOptional.get().getId());
    }
}
