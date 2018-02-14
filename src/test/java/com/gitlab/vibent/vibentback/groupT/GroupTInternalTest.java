package com.gitlab.vibent.vibentback.groupT;

import com.gitlab.vibent.vibentback.VibentTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class GroupTInternalTest extends VibentTest {

    @Autowired
    @InjectMocks
    private GroupTController controller;

    @MockBean
    private GroupTRepository groupTRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(groupTRepository.findByRef(RANDOM_GROUP.getRef())).thenReturn(RANDOM_GROUP);
        when(groupTRepository.save(RANDOM_GROUP)).thenReturn(RANDOM_GROUP);
        when(groupTRepository.deleteByRef(RANDOM_GROUP.getRef())).thenReturn(1);
    }

    @Test
    public void getGroupT(){
        GroupT groupT = controller.getGroupT(RANDOM_GROUP.getRef());
        Assert.assertEquals(RANDOM_GROUP.getRef(), groupT.getRef());
    }

    @Test
    public void addGroupT(){
        GroupT groupT = controller.createGroupT(RANDOM_GROUP);
        Assert.assertEquals(RANDOM_GROUP.getRef(), groupT.getRef());
    }

    @Test
    public void deleteGroupT(){
        controller.deleteGroupT(RANDOM_GROUP.getRef());
    }
}
