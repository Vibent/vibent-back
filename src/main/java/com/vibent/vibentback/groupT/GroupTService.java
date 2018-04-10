package com.vibent.vibentback.groupT;

import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupTService {

    GroupTRepository groupTRepository;

    public GroupT getGroupT(String ref) {
        return groupTRepository.findByRef(ref)
                .orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
    }

    public ArrayList<GroupT> getGroupT() {
        //TODO update

        /*User user = new User();
        ArrayList<GroupMembership> groupMemberships = getUserGroupMemberships.findAllByUserRef(user.getRef());
        ArrayList<GroupT> groupTArrayList = new ArrayList<>();
        for (GroupMembership groupLinkToUserTest: groupMemberships) {
            GroupT group = groupTRepository.findByRef(groupLinkToUserTest.getGroupRef());
            if(group == null)
                throw new VibentException(VibentError.GROUP_NOT_FOUND);
            groupTArrayList.add(group);
        }
        return groupTArrayList;
        */
        return null;
    }

    public GroupT addGroupT(GroupT groupT) {
        return groupTRepository.save(groupT);
    }

    public void deleteGroupT(String groupRef) {
        groupTRepository.deleteByRef(groupRef);
    }

    public GroupT updateGroupT(String groupRef, GroupT newGroup) {
        GroupT existing = groupTRepository.findByRef(groupRef)
                .orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        ObjectUpdater.updateProperties(existing, newGroup);
        return groupTRepository.save(existing);
    }

}
