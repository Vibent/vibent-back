package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.survey.option.SurveyOption;
import com.vibent.vibentback.bubble.survey.option.SurveyOptionRepository;
import com.vibent.vibentback.bubble.survey.usersAnswers.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.usersAnswers.SurveyAnswersRepository;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.api.bubble.survey.SurveyAnswerRequest;
import com.vibent.vibentback.api.bubble.survey.SurveyBubbleUpdateRequest;
import com.vibent.vibentback.api.bubble.survey.SurveyOptionRequest;
import com.vibent.vibentback.api.bubble.survey.SurveyOptionUpdateRequest;
import com.vibent.vibentback.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SurveyService {

    SurveyBubbleRepository bubbleRepository;
    SurveyOptionRepository optionRepository;
    SurveyAnswersRepository surveyAnswersRepository;
    EventRepository eventRepository;
    UserRepository userRepository;
    ConnectedUserUtils userUtils;

    // Survey Bubble -------------------------------------------------------------
    public SurveyBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public SurveyBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        SurveyBubble surveyBubble = new SurveyBubble();
        surveyBubble.setEvent(event);
        surveyBubble.setCreator(userUtils.getConnectedUser());
        surveyBubble.setType(BubbleType.SurveyBubble);
        surveyBubble = bubbleRepository.save(surveyBubble);
        return surveyBubble;
    }

    public SurveyBubble updateBubble(long id, SurveyBubbleUpdateRequest update) {
        SurveyBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        bubble.setTitle(update.getTitle());
        bubbleRepository.save(bubble);
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
        optionRepository.save(option);
        return bubble;
    }

    public SurveyBubble updateOption(Long id, SurveyOptionUpdateRequest request) {
        SurveyOption option = optionRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.SURVEY_OPTION_NOT_FOUND));
        option.setContent(request.getContent());
        option = optionRepository.save(option);
        return option.getBubble();
    }

    public void deleteOption(Long id) {
        optionRepository.deleteById(id);
    }

    // Survey Bubble Answer -------------------------------------------------------------

    public SurveyBubble createAnswer(SurveyAnswerRequest request) {
        SurveyOption option = optionRepository.findById(request.getSurveyOptionId())
                .orElseThrow(() -> new VibentException(VibentError.SURVEY_OPTION_NOT_FOUND));
        SurveyAnswer answer = new SurveyAnswer();
        answer.setUser(userUtils.getConnectedUser());
        answer.setOption(option);
        surveyAnswersRepository.save(answer);
        return option.getBubble();
    }

    public void deleteAnswer(Long id) {
        surveyAnswersRepository.deleteById(id);
    }
}
