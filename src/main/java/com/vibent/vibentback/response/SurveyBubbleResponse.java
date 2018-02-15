package com.vibent.vibentback.response;

import com.vibent.vibentback.bubble.survey.SurveyBubble;
import com.vibent.vibentback.bubble.survey.response.SurveyResponse;
import com.vibent.vibentback.bubble.survey.usersResponses.UsersSurveyResponses;
import lombok.Data;

import java.util.ArrayList;

@Data
public class SurveyBubbleResponse {
    private SurveyBubble surveyBubble;
    private ArrayList<SurveyResponse> surveyResponses;
    private ArrayList<UsersSurveyResponses> usersSurveyResponses;
}
