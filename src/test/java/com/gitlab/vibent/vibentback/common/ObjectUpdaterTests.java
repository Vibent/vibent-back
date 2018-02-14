package com.gitlab.vibent.vibentback.common;

import com.gitlab.vibent.vibentback.VibentTest;
import com.gitlab.vibent.vibentback.user.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class ObjectUpdaterTests extends VibentTest {

    @Test
    public void objectUpdaterTest(){
        // Set up
        User user = new User();
        user.setFirstName("John");
        String oldRef = RANDOM_USER.getRef();
        RANDOM_USER.setIsDeleted(false);
        boolean oldIsDeleted = RANDOM_USER.getIsDeleted();
        // Method to test
        ObjectUpdater.updateProperties(user, RANDOM_USER);
        // Check
        Assert.assertEquals(RANDOM_USER.getRef(), oldRef);
        Assert.assertEquals(RANDOM_USER.getFirstName(), "John");
        Assert.assertEquals(RANDOM_USER.getIsDeleted(), oldIsDeleted);
    }

    @Test
    public void objectUpdaterCantUpdateRefOrIdTest(){
        // Set up
        User user = new User();
        user.setId(5L);
        user.setRef(UUID.randomUUID().toString());
        RANDOM_USER.setId(10L);
        // Method to test
        ObjectUpdater.updateProperties(user, RANDOM_USER);
        // Check
        Assert.assertNotEquals(RANDOM_USER.getRef(), user.getRef());
        Assert.assertNotEquals(RANDOM_USER.getId(), user.getId());
    }
}
