package com.vibent.vibentback.bubble.survey;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.survey.SurveyBubble;
import com.vibent.vibentback.bubble.survey.SurveyBubbleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyBubbleDataTest extends VibentTest {

    @Autowired
    SurveyBubbleRepository repository;

    @Test
    public void testAddSurveyBubble(){
        SurveyBubble surveyBubble = new SurveyBubble("survey question");
        repository.save(surveyBubble);
    }

}
