package com.vibent.vibentback.api.bubble.survey;

import com.vibent.vibentback.bubble.survey.SurveyBubble;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswers;
import com.vibent.vibentback.common.ObjectUpdater;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class SurveyBubbleRes extends SurveyBubble {

    public SurveyBubbleRes(SurveyBubble bubble) {
        super();
        ObjectUpdater.updateAllProperties(bubble, this);
        answers = new ArrayList<>();
    }

    public void addAnswer(SurveyAnswer answer, Iterable<UsersSurveyAnswers> usersSurveyAnswers) {
        SurveyAnswerRes res = new SurveyAnswerRes(answer);
        usersSurveyAnswers.forEach(res::addUserAnswer);
        answers.add(res);
    }

    ArrayList<SurveyAnswer> answers;

    @Getter
    @Setter
    private class SurveyAnswerRes extends SurveyAnswer {
        ArrayList<UsersSurveyAnswersRes> usersSurveyAnswersRes;

        public SurveyAnswerRes(SurveyAnswer answer) {
            super();
            ObjectUpdater.updateAllProperties(answer, this);
            usersSurveyAnswersRes = new ArrayList<>();
        }

        public void addUserAnswer(UsersSurveyAnswers usersSurveyAnswer) {
            usersSurveyAnswersRes.add(new UsersSurveyAnswersRes(usersSurveyAnswer));
        }

        @Setter
        @Getter
        private class UsersSurveyAnswersRes extends UsersSurveyAnswers {
            public UsersSurveyAnswersRes(UsersSurveyAnswers usersSurveyAnswer) {
                super();
                ObjectUpdater.updateAllProperties(usersSurveyAnswer, this);
            }
        }
    }
}
