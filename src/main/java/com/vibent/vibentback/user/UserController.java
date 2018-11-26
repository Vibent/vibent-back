package com.vibent.vibentback.user;

import com.vibent.vibentback.api.auth.MailConfirmationResponse;
import com.vibent.vibentback.api.user.DetailledUserResponse;
import com.vibent.vibentback.api.user.EmailChangeRequest;
import com.vibent.vibentback.api.user.SimpleUserResponse;
import com.vibent.vibentback.api.user.UpdateUserRequest;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize(value = "hasPermission(#userRef, 'User', 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/{userRef}")
    SimpleUserResponse getUser(@PathVariable String userRef) {
        log.info("Get user with ref : {}", userRef);
        return new SimpleUserResponse(userService.getUserByRef(userRef));
    }

    @PreAuthorize(value = "hasPermission(#userRef, 'User', 'write')")
    @RequestMapping(method = RequestMethod.PATCH, value = "/{userRef}")
    DetailledUserResponse updateUser(@PathVariable String userRef, @Valid @RequestBody UpdateUserRequest request) {
        log.info("Update request with ref {} body : {}", userRef, request.toString());
        return new DetailledUserResponse(userService.updateUser(userRef, request));
    }

    @PreAuthorize(value = "hasPermission(#userRef, 'User', 'write')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{userRef}")
    void deleteUser(@PathVariable String userRef) {
        log.info("Deleting user with ref : {}", userRef);
        userService.deleteUser(userRef);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changeEmail")
    public MailConfirmationResponse changeEmail(@Valid @RequestBody EmailChangeRequest request) {
        log.info("Requested changing email to {}", request.getEmail());
        String email = userService.changeEmail(request);
        return new MailConfirmationResponse(email);
    }
}
