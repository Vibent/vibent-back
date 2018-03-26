package com.vibent.vibentback.bubble.planning;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.planning.PlanningBubble;
import com.vibent.vibentback.bubble.planning.PlanningBubbleRepository;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlanningBubbleDataTest extends VibentTest {

    @Autowired
    PlanningBubbleRepository repository;

    @Test
    public void testAddPlanningBubble(){
        PlanningBubble planningBubble = new PlanningBubble();
        repository.save(planningBubble);
    }

}
