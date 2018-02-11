package com.gitlab.vibent.vibentback.groupT;

import com.gitlab.vibent.vibentback.VibentTests;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupTTests extends VibentTests {

    @Autowired
    GroupTRepository repository;

    @Test
    public void testAddGroup(){
        GroupT group = new GroupT(UUID.randomUUID().toString(), "groupTest");
        repository.save(group);
    }
}
