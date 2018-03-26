package com.vibent.vibentback.bubble.location;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.bubble.location.LocationBubble;
import com.vibent.vibentback.bubble.location.LocationBubbleRepository;
import javax.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class LocationBubbleDataTest extends VibentTest {

    @Autowired
    LocationBubbleRepository repository;

    @Test
    public void testAddLocationBubble(){
        LocationBubble locationBubble = new LocationBubble("{45.7866307,4.8835191,13z}");
        repository.save(locationBubble);
    }

}
