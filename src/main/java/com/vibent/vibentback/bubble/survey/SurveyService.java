package com.vibent.vibentback.bubble.survey;


import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.api.bubble.survey.*;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswerRepository;
import com.vibent.vibentback.bubble.survey.option.SurveyOption;
import com.vibent.vibentback.bubble.survey.option.SurveyOptionRepository;
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
public class SurveyService {

    SurveyBubbleRepository bubbleRepository;
    SurveyOptionRepository optionRepository;
    SurveyAnswerRepository answerRepository;
    EventRepository eventRepository;
    ConnectedUserUtils userUtils;

    // Survey Bubble -------------------------------------------------------------
    public SurveyBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public SurveyBubble createBubble(SurveyBubbleRequest request) {
        Event event = eventRepository.findByRef(request.getEventRef())
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        SurveyBubble surveyBubble = new SurveyBubble();
        surveyBubble.setEvent(event);
        surveyBubble.setTitle(request.getTitle());
        surveyBubble.setCreator(userUtils.getConnectedUser());
        surveyBubble.setDeleted(false);
        surveyBubble.setType(BubbleType.SurveyBubble);
        surveyBubble = bubbleRepository.save(surveyBubble);
        return surveyBubble;
    }

    public SurveyBubble updateBubble(long id, SurveyBubbleUpdateRequest request) {
        SurveyBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        bubble.setTitle(request.getTitle());
        bubble = bubbleRepository.save(bubble);
        return bubble;
    }

    public void deleteBubble(long id) {
        bubbleRepository.deleteById(id);
    }

    // Survey Bubble Option -------------------------------------------------------------

    public SurveyBubble createOption(SurveyOptionRequest request) {
        SurveyBubble bubble = bubbleRepository.findById(request.getBubbleId())
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        SurveyOption option = new SurveyOption();
        option.setBubble(bubble);
        option.setContent(request.getContent());
        option.setUser(userUtils.getConnectedUser());
        bubble.getOptions().add(option);
        return bubbleRepository.save(bubble);
    }

    public SurveyBubble updateOption(Long id, SurveyOptionUpdateRequest request) {
        SurveyOption option = optionRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.OPTION_NOT_FOUND));
        option.setContent(request.getContent());
        optionRepository.save(option);
        return bubbleRepository.findById(option.getBubble().getId())
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public void deleteOption(Long id) {
        SurveyOption option = optionRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.OPTION_NOT_FOUND));
        SurveyBubble bubble = option.getBubble();
        bubble.getOptions().remove(option);
        optionRepository.delete(option);
        bubbleRepository.save(bubble);
    }

    // Survey Bubble Answer -------------------------------------------------------------

    public SurveyBubble createAnswer(SurveyAnswerRequest request) {
        SurveyOption option = optionRepository.findById(request.getOptionId())
                .orElseThrow(() -> new VibentException(VibentError.OPTION_NOT_FOUND));
        if (option.getAnswers().stream().anyMatch(a -> a.getUserRef().equals(userUtils.getConnectedUserRef()))) {
            throw new VibentException(VibentError.ANSWER_ALREADY_CREATED);
        }
        SurveyAnswer answer = new SurveyAnswer();
        answer.setUser(userUtils.getConnectedUser());
        answer.setOption(option);
        option.getAnswers().add(answer);
        optionRepository.save(option);
        return option.getBubble();
    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
        SurveyAnswer answer = answerRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.ANSWER_NOT_FOUND));
        SurveyOption option = answer.getOption();
        option.getAnswers().remove(answer);
        answerRepository.delete(answer);
        optionRepository.save(option);
    }

    public void deleteAnswerOfOption(Long id) {
        SurveyOption option = optionRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.OPTION_NOT_FOUND));
        Optional<SurveyAnswer> answerOptional = option.getAnswers().stream().filter(
                a -> a.getUserRef().equals(userUtils.getConnectedUserRef())).findFirst();
        if (!answerOptional.isPresent()) {
            throw new VibentException(VibentError.ANSWER_NOT_FOUND);
        }
        this.deleteAnswer(answerOptional.get().getId());
    }
}
