package com.vibent.vibentback.user;

import com.vibent.vibentback.api.user.DetailledUserResponse;
import com.vibent.vibentback.api.user.SimpleUserResponse;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/user",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "user", description = "Operations pertaining to user management")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/me")
    DetailledUserResponse getConnectedUser() {
        log.info("Get connected user");
        return new DetailledUserResponse(userService.getConnectedUser());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userRef}")
    SimpleUserResponse getUser(@PathVariable String userRef) {
        log.info("Get user with ref : {}", userRef);
        return new SimpleUserResponse(userService.getUserByRef(userRef));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{userRef}")
    SimpleUserResponse updateUser(@PathVariable String userRef, @Valid @RequestBody User user) {
        log.info("Update user with ref {} body : {}", userRef, user.toString());
        return new SimpleUserResponse(userService.updateUser(userRef, user));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{userRef}")
    void deleteUser(@PathVariable String userRef) {
        log.info("Deleting user with ref : {}", userRef);
        userService.deleteUser(userRef);
    }
}
