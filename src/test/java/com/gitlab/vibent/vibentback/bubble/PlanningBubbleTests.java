package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentTests;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubble;
import com.gitlab.vibent.vibentback.bubble.checkbox.CheckboxBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.free.FreeBubble;
import com.gitlab.vibent.vibentback.bubble.free.FreeBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.location.LocationBubble;
import com.gitlab.vibent.vibentback.bubble.location.LocationBubbleRepository;
import com.gitlab.vibent.vibentback.bubble.planning.PlanningBubble;
import com.gitlab.vibent.vibentback.bubble.planning.PlanningBubbleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanningBubbleTests extends VibentTests {

    @Autowired
    PlanningBubbleRepository repository;

    @Test
    public void testAddPlanningBubble(){
        PlanningBubble planningBubble = new PlanningBubble();
        repository.save(planningBubble);
    }

}
