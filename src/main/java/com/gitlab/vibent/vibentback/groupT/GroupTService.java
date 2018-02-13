package com.gitlab.vibent.vibentback.groupT;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.acl.Group;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupTService {

    GroupTRepository groupTRepository;

    public GroupT getGroupT(String ref){
        return groupTRepository.findByRef(ref);
    }
}
