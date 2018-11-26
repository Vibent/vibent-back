package com.vibent.vibentback.user;

import com.vibent.vibentback.VibentTest;
import com.vibent.vibentback.api.user.SimpleUserResponse;
import com.vibent.vibentback.api.user.UpdateUserRequest;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.permission.VibentPermissionEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserInternalTest extends VibentTest {

    @Autowired
    @InjectMocks
    private UserController controller;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    VibentPermissionEvaluator permissionEvaluator;

    @Before
    public void setUp() {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        when(permissionEvaluator.hasPermission(any(), any(), any(), any())).thenReturn(false);
        when(permissionEvaluator.hasPermission(eq(AUTHENTICATION), any(), any(), any())).thenReturn(true);
        when(userRepository.findByRef(RANDOM_USER.getRef())).thenReturn(Optional.ofNullable(RANDOM_USER));
        when(userRepository.save(RANDOM_USER)).thenReturn(RANDOM_USER);
        when(userRepository.deleteByRef(RANDOM_USER.getRef())).thenReturn(1);
    }

    @Test
    public void getUser() {
        SimpleUserResponse user = controller.getUser(RANDOM_USER.getRef());
        Assert.assertEquals(RANDOM_USER.getRef(), user.getRef());
    }

    @Test
    public void getNonExistingUser() throws VibentException {
        exception.expect(VibentException.class);
        exception.expectMessage("user_not_found");

        controller.getUser("doesntExist");
    }

    @Test
    public void deleteUser() {
        controller.deleteUser(RANDOM_USER.getRef());
    }

    @Test
    public void updateUser() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setFirstName("newFirst");
        controller.updateUser(RANDOM_USER.getRef(), request);
    }


}
