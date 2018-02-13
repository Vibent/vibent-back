package com.gitlab.vibent.vibentback.bubble;

import com.gitlab.vibent.vibentback.VibentInternalTest;
import com.gitlab.vibent.vibentback.bubble.location.LocationBubble;
import com.gitlab.vibent.vibentback.bubble.location.LocationBubbleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationBubbleDataTest extends VibentInternalTest {

    @Autowired
    LocationBubbleRepository repository;

    @Test
    public void testAddLocationBubble(){
        LocationBubble locationBubble = new LocationBubble("{45.7866307,4.8835191,13z}");
        repository.save(locationBubble);
    }

}
