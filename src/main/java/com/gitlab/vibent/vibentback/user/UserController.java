package com.gitlab.vibent.vibentback.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(method = RequestMethod.POST, value = "/")
    User createUser(@RequestBody User user){
        log.info("Creating user with body : {}", user.toString());
        return userService.addUser(user);
    }
}
