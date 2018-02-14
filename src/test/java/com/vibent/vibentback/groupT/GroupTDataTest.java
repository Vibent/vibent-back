package com.vibent.vibentback.groupT;

import com.vibent.vibentback.VibentTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupTDataTest extends VibentTest {

    @Autowired
    GroupTRepository repository;

    @Before
    public void setUp() {
        repository.save(RANDOM_GROUP);
    }

    @After
    public void tearDown() {
        repository.deleteByRef(RANDOM_GROUP.getRef());
    }

    @Test
    public void testAddGroup(){
        GroupT group = new GroupT(UUID.randomUUID().toString(), "groupTest");
        repository.save(group);
        Assert.assertNotNull(group.getRef());

        // Clean up
        repository.delete(group);
    }

    @Test
    public void testGetGroupT() {
        GroupT groupT = repository.findByRef(RANDOM_GROUP.getRef());
        Assert.assertNotNull(groupT.getRef());
    }

    @Test
    public void testDeleteGroupT() {
        Integer deletedAmount = repository.deleteByRef(RANDOM_GROUP.getRef());
        Assert.assertEquals(1, deletedAmount.intValue());
    }
}