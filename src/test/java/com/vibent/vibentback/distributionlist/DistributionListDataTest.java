package com.vibent.vibentback.distributionlist;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.distributionlist.membership.DistributionListMembership;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Collections;

@Slf4j
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class DistributionListDataTest extends VibentTest {

    @Autowired
    DistributionListRepository repository;

    @Before
    public void setUp() {
        super.setUp();
        RANDOM_DISTRIBUTION_LIST = repository.save(RANDOM_DISTRIBUTION_LIST);
    }

    @Test
    public void testAddDistributionList() {
        DistributionList distributionList = new DistributionList();
        distributionList.setTitle("Distribution list");
        distributionList.setDescription("Descr");
        distributionList.setCreator(RANDOM_USER);
        DistributionListMembership membership = new DistributionListMembership();
        membership.setUser(RANDOM_USER);
        membership.setDistributionList(distributionList);
        distributionList.setMemberships(Collections.singleton(membership));
        repository.save(distributionList);
    }

    @Test
    public void testGetEvent() {
        DistributionList distributionList = repository.findById(RANDOM_DISTRIBUTION_LIST.getId())
                .orElseThrow(() -> new VibentException(VibentError.DISTRIBUTION_LIST_NOT_FOUND));
        Assert.assertNotNull(distributionList.getId());
    }

    @Test
    public void testDeleteEvent() {
        repository.deleteById(RANDOM_DISTRIBUTION_LIST.getId());
        Assert.assertFalse(repository.findById(RANDOM_DISTRIBUTION_LIST.getId()).isPresent());
    }
}
