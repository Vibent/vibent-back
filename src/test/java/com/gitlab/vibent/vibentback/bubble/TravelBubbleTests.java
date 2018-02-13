package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.free.FreeBubble;
import com.gitlab.vibent.vibentback.bubble.free.FreeBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.survey.SurveyBubble;
import com.gitlab.vibent.vibentback.bubble.survey.SurveyBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.travel.TravelBubble;
import com.gitlab.vibent.vibentback.bubble.travel.TravelBubbleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TravelBubbleTests extends VibentTests {

    @Autowired
    TravelBubbleRepository repository;

    @Test
    public void testAddTravelBubble(){
        TravelBubble travelBubble = new TravelBubble();
        repository.save(travelBubble);
    }

}
