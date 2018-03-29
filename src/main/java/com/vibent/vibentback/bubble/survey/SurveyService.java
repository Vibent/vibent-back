package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.Mock;
import com.vibent.vibentback.api.bubble.survey.*;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswerRepository;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswers;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswersRepository;
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
public class SurveyService {

    SurveyBubbleRepository bubbleRepository;
    SurveyAnswerRepository answerRepository;
    UsersSurveyAnswersRepository usersSurveyAnswersRepository;
    EventRepository eventRepository;
    UserRepository userRepository;

    // Survey Bubble -------------------------------------------------------------
    public SurveyBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public SurveyBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        SurveyBubble surveyBubble = new SurveyBubble();
        surveyBubble.setEvent(event);
        surveyBubble.setCreator(Mock.getConnectedUser(userRepository));
        surveyBubble.setDeleted(false);
        surveyBubble.setType(BubbleType.SurveyBubble);
        surveyBubble = bubbleRepository.save(surveyBubble);
        return surveyBubble;
    }

    public SurveyBubble updateBubble(long id, SurveyBubbleUpdateRequest update) {
        SurveyBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        ObjectUpdater.updateProperties(update, bubble);
        bubbleRepository.save(bubble);
        return bubble;
    }

    public void deleteBubble(long id) {
        bubbleRepository.deleteById(id);
    }

    // Survey Bubble Answer -------------------------------------------------------------

    public SurveyBubble createAnswer(SurveyAnswerRequest request) {
        SurveyBubble bubble = bubbleRepository.findById(request.getBubbleId())
                .orElseThrow(() -> new VibentException(VibentError.ENTRY_NOT_FOUND));
        SurveyAnswer answer = new SurveyAnswer();
        answer.setBubble(bubble);
        answer.setContent(request.getContent());
        answer.setDeleted(false);
        answerRepository.save(answer);
        return bubble;
    }

    public SurveyBubble updateAnswer(Long id, SurveyAnswerUpdateRequest request) {
        SurveyAnswer answer = answerRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.ANSWER_NOT_FOUND));
        ObjectUpdater.updateProperties(request, answer);
        answerRepository.save(answer);
        return answer.getBubble();
    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }

    // Survey Bubble user entry -------------------------------------------------------------

    public SurveyBubble createUserAnswer(UsersSurveyAnswersRequest request) {
        SurveyAnswer answer = answerRepository.findById(request.getSurveyAnswerId())
                .orElseThrow(() -> new VibentException(VibentError.ANSWER_NOT_FOUND));
        UsersSurveyAnswers usersSurveyAnswer = new UsersSurveyAnswers();
        usersSurveyAnswer.setUser(Mock.getConnectedUser(userRepository));
        usersSurveyAnswer.setAnswer(answer);
        usersSurveyAnswer.setDeleted(false);
        usersSurveyAnswersRepository.save(usersSurveyAnswer);
        return answer.getBubble();
    }

    public void deleteUserAnswer(Long id) {
        usersSurveyAnswersRepository.deleteById(id);
    }
}
