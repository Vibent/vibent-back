package com.vibent.vibentback.group;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.group.api.GroupRequest;
import com.vibent.vibentback.group.api.GroupUpdateRequest;
import com.vibent.vibentback.group.membership.MembershipService;
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
@WebMvcTest(value = GroupTController.class, secure = false)
public class GroupTWebLayerTest extends VibentTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    GroupTService groupTService;

    @MockBean
    MembershipService membershipService;

    private GroupRequest RANDOM_GROUP_REQUEST;
    private GroupUpdateRequest RANDOM_GROUP_UPDATE_REQUEST;

    @Before
    public void setUp(){
        super.setUp();

        RANDOM_GROUP_REQUEST = new GroupRequest();
        RANDOM_GROUP_REQUEST.setName("Name for group");
        RANDOM_GROUP_REQUEST.setAllAdmins(true);

        RANDOM_GROUP_UPDATE_REQUEST = new GroupUpdateRequest();
        RANDOM_GROUP_UPDATE_REQUEST.setName("New name");

        when(groupTService.getGroupT(RANDOM_GROUP.getRef())).thenReturn(RANDOM_GROUP);
        when(groupTService.createGroupT(RANDOM_GROUP_REQUEST)).thenReturn(RANDOM_GROUP);
        when(groupTService.updateGroupT(RANDOM_GROUP.getRef(), RANDOM_GROUP_UPDATE_REQUEST)).thenReturn(RANDOM_GROUP);
    }

    @Test
    public void testGetGroupT() throws Exception {
        this.mockMvc.perform(get("/group/" + RANDOM_GROUP.getRef())).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    public void testAddGroupT() throws Exception {
        String body = super.getJsonString(RANDOM_GROUP_REQUEST);

        mockMvc.perform(post("/group").contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    public void testUpdateGroup() throws Exception {
        String body = super.getJsonString(RANDOM_GROUP_UPDATE_REQUEST);

        mockMvc.perform(patch("/group/" + RANDOM_GROUP.getRef()).contentType(APPLICATION_JSON_UTF8)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("name")));
    }

    @Test
    public void testDeleteGroupT() throws Exception {
        mockMvc.perform(delete("/group/" + RANDOM_GROUP.getRef()))
                .andExpect(status().isNoContent());
    }
}