package com.vibent.vibentback.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/{userRef}")
    User getUser(@PathVariable String userRef) {
        log.info("Get user with ref : {}", userRef);
        return userService.getUser(userRef);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    User createUser(@RequestBody User user){
        log.info("Creating user with body : {}", user.toString());
        return userService.addUser(user);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{userRef}")
    User updateUser(@PathVariable String userRef, @RequestBody User user){
        log.info("Update user with ref {} body : {}", userRef, user.toString());
        return userService.updateUser(userRef, user);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{userRef}")
    void deleteUser(@PathVariable String userRef){
        log.info("Deleting user with ref : {}", userRef);
        userService.deleteUser(userRef);
    }
}
