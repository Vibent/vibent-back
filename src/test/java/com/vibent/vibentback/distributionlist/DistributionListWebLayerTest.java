package com.vibent.vibentback.distributionlist;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.distributionlist.api.DistributionListRequest;
import com.vibent.vibentback.distributionlist.api.DistributionListUpdateRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DistributionListController.class, secure = false)
public class DistributionListWebLayerTest extends VibentTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    DistributionListService distributionListService;

    private DistributionListRequest RANDOM_DISTRIBUTION_LIST_REQUEST;
    private DistributionListUpdateRequest RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST;

    @Before
    public void setUp() {
        super.setUp();

        RANDOM_DISTRIBUTION_LIST_REQUEST = new DistributionListRequest();
        RANDOM_DISTRIBUTION_LIST_REQUEST.setEventRef(RANDOM_EVENT.getRef());
        RANDOM_DISTRIBUTION_LIST_REQUEST.setDescription("Descript");
        RANDOM_DISTRIBUTION_LIST_REQUEST.setTitle("Titlelittle");

        RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST = new DistributionListUpdateRequest();
        RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST.setTitle("New title");
        RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST.setDescription("New Desc");

        when(distributionListService.getDistributionList(RANDOM_DISTRIBUTION_LIST.getId())).thenReturn(RANDOM_DISTRIBUTION_LIST);
        when(distributionListService.createDistributionList(RANDOM_DISTRIBUTION_LIST_REQUEST)).thenReturn(RANDOM_DISTRIBUTION_LIST);
        when(distributionListService.updateDistributionList(RANDOM_DISTRIBUTION_LIST.getId(), RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST)).thenReturn(RANDOM_DISTRIBUTION_LIST);
    }

    @Test
    public void testGetDistributionList() throws Exception {
        this.mockMvc.perform(get("/distribution-list/" + RANDOM_DISTRIBUTION_LIST.getId())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    public void testCreateDistributionList() throws Exception {
        String body = super.getJsonString(RANDOM_DISTRIBUTION_LIST_REQUEST);

        mockMvc.perform(post("/distribution-list").contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    public void testUpdateDistributionList() throws Exception {
        String body = super.getJsonString(RANDOM_DISTRIBUTION_LIST_UPDATE_REQUEST);

        mockMvc.perform(patch("/distribution-list/" + RANDOM_DISTRIBUTION_LIST.getId()).contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("title")));
    }

    @Test
    public void testDeleteDistributionList() throws Exception {
        mockMvc.perform(delete("/distribution-list/" + RANDOM_DISTRIBUTION_LIST.getId()))
                .andExpect(status().isNoContent());
    }
}