package com.vibent.vibentback.response;

import com.vibent.vibentback.bubble.survey.SurveyBubble;
import com.vibent.vibentback.bubble.survey.answer.SurveyAnswer;
import com.vibent.vibentback.bubble.survey.usersAnswers.UsersSurveyAnswers;
import lombok.Data;

import java.util.ArrayList;

@Data
public class SurveyBubbleResponse {
    private SurveyBubble surveyBubble;
    private ArrayList<SurveyAnswer> surveyAnswers;
    private ArrayList<UsersSurveyAnswers> usersSurveyAnswers;
}
