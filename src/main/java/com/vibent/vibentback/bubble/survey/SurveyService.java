package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.api.bubble.alimentation.AlimentationBubbleRes;
import com.vibent.vibentback.api.bubble.survey.*;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.vibent.vibentback.bubble.ownership.BubbleOwnership;
import com.vibent.vibentback.bubble.ownership.BubbleOwnershipRepository;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswerRepository;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswers;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswersRepository;
import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SurveyService {

    BubbleOwnershipRepository ownershipRepository;
    SurveyBubbleRepository bubbleRepository;
    SurveyAnswerRepository answerRepository;
    UsersSurveyAnswersRepository usersSurveyAnswersRepository;
    EventRepository eventRepository;

    // Alimentation Bubble -------------------------------------------------------------
    public SurveyBubbleRes getBubbleResponse(long id) {
        SurveyBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        return getBubbleResponse(bubble);
    }

    public SurveyBubbleRes getBubbleResponse(SurveyBubble bubble) {
        SurveyBubbleRes response = new SurveyBubbleRes(bubble);
        Iterable<SurveyAnswer> answers = answerRepository.findByBubbleId(bubble.getId());
        for (SurveyAnswer answer : answers) {
            Iterable<UsersSurveyAnswers> usersSurveyAnswers = usersSurveyAnswersRepository.findBySurveyAnswerId(answer.getId());
            response.addAnswer(answer, usersSurveyAnswers);
        }
        return response;
    }

    public SurveyBubbleRes getBubble(long id) {
        return getBubbleResponse(id);
    }

    public SurveyBubbleRes createBubble(SurveyBubbleReq request) {
        Event event = eventRepository.findByRef(request.getEventRef())
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        SurveyBubble surveyBubble = bubbleRepository.save(new SurveyBubble());
        ownershipRepository.save(new BubbleOwnership(event.getRef(),
                surveyBubble.getId(),
                BubbleType.SurveyBubble,
                "CREATOR")); // TODO add creator as connected user
        return getBubbleResponse(surveyBubble);
    }

    public SurveyBubbleRes updateBubble(long id, SurveyBubble bubble) {
        SurveyBubble old = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        ObjectUpdater.updateProperties(bubble, old);
        bubble = bubbleRepository.save(old);
        return getBubbleResponse(bubble);
    }

    public void deleteBubble(long id) {
        SurveyBubble bubble = bubbleRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        BubbleOwnership ownership = ownershipRepository.findByIdAndBubbleType(id, BubbleType.AlimentationBubble)
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        ownershipRepository.deleteById(ownership.getId());
    }

    // Survey Bubble answer -------------------------------------------------------------

    public SurveyBubbleRes createBubbleAnswer(SurveyAnswerReq request) {
        if( ! bubbleRepository.existsById(request.getBubbleId()))
            throw new VibentException(VibentError.BUBBLE_NOT_FOUND);
        SurveyAnswer entry = new SurveyAnswer();
        ObjectUpdater.updateProperties(request, entry);
        answerRepository.save(entry);
        return getBubbleResponse(request.getBubbleId());
    }

    public SurveyBubbleRes updateBubbleAnswer(Long id, SurveyAnswerUpdateReq update) {
        SurveyAnswer answer = answerRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.ANSWER_NOT_FOUND));
        ObjectUpdater.updateProperties(update, answer);
        answerRepository.save(answer);
        return getBubbleResponse(answer.getBubbleId());
    }

    public void deleteBubbleAnswer(Long id) {
        if(! answerRepository.existsById(id))
            throw new VibentException(VibentError.ANSWER_NOT_FOUND);
        answerRepository.deleteById(id);
    }

    // Survey Bubble user answer -------------------------------------------------------------

    public SurveyBubbleRes createBubbleUserAnswer(UsersSurveyAnswersReq request) {
        SurveyAnswer answer = answerRepository.findById(request.getSurveyAnswerId())
                .orElseThrow(() -> new VibentException(VibentError.ANSWER_NOT_FOUND));
        UsersSurveyAnswers usersSurveyAnswers = new UsersSurveyAnswers();
        ObjectUpdater.updateProperties(request, usersSurveyAnswers);
        usersSurveyAnswers.setUserRef("CREATOR"); // TODO replace with connected user
        usersSurveyAnswersRepository.save(usersSurveyAnswers);
        return getBubbleResponse(answer.getBubbleId());
    }

    public void deleteBubbleUserAnswer(Long id) {
        if(! usersSurveyAnswersRepository.existsById(id))
            throw new VibentException(VibentError.USER_ANSWER_NOT_FOUND);
        usersSurveyAnswersRepository.deleteById(id);
    }
}
