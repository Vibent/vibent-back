package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentInternalTest;
import com.gitlab.vibent.vibentback.bubble.planning.PlanningBubble;
import com.gitlab.vibent.vibentback.bubble.planning.PlanningBubbleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanningBubbleDataTest extends VibentInternalTest {

    @Autowired
    PlanningBubbleRepository repository;

    @Test
    public void testAddPlanningBubble(){
        PlanningBubble planningBubble = new PlanningBubble();
        repository.save(planningBubble);
    }

}
