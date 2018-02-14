package com.vibent.vibentback.groupT;

import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupTService {

    GroupTRepository groupTRepository;

    public GroupT getGroupT(String ref) {
        return groupTRepository.findByRef(ref);
    }

    public GroupT addGroupT(GroupT groupT) {
        return groupTRepository.save(groupT);
    }

    public void deleteGroupT(String groupRef) {
        GroupT groupT = groupTRepository.findByRef(groupRef);
        groupT.setDeleted(true);
        groupTRepository.save(groupT);
    }

    public GroupT updateGroupT(String groupRef, GroupT newGroup) {
        GroupT existing = groupTRepository.findByRef(groupRef);
        ObjectUpdater.updateProperties(existing, newGroup);
        return groupTRepository.save(existing);
    }

}
