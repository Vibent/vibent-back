package com.vibent.vibentback.groupT;

import com.vibent.vibentback.api.groupT.GroupRequest;
import com.vibent.vibentback.api.groupT.GroupUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/group")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupTController {

    private final GroupTService groupTService;

    @RequestMapping(method = RequestMethod.GET, value = "/{groupRef}")
    GroupT getGroupT(@PathVariable String groupRef) {
        log.info("Get group with ref : {}", groupRef);
        return groupTService.getGroupT(groupRef);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    GroupT createGroupT(@Valid @RequestBody GroupRequest request){
        log.info("Creating group with body : {}", request.toString());
        return groupTService.createGroupT(request);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{groupRef}")
    GroupT updateGroupT(@PathVariable String groupRef, @Valid @RequestBody GroupUpdateRequest request){
        log.info("Update group with ref {} body : {}", groupRef, request.toString());
        return groupTService.updateGroupT(groupRef, request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/{groupRef}")
    void deleteGroupT(@PathVariable String groupRef){
        log.info("Deleting group with ref : {}", groupRef);
        groupTService.deleteGroupT(groupRef);
    }
}
