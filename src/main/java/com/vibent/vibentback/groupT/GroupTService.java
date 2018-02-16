package com.vibent.vibentback.groupT;

import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.groupMembership.GroupMembership;
import com.vibent.vibentback.groupMembership.GroupMembershipRepository;
import com.vibent.vibentback.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupTService {

    GroupTRepository groupTRepository;
    GroupMembershipRepository getUserGroupMemberships;

    public GroupT getGroupT(String ref) {
        return groupTRepository.findByRef(ref);
    }

    public ArrayList<GroupT> getGroupT() {
        //TODO Get current user
        User user = new User();
        ArrayList<GroupMembership> groupMemberships = getUserGroupMemberships.findAllByUserRef(user.getRef());
        ArrayList<GroupT> groupTArrayList = new ArrayList<>();
        for (GroupMembership groupMembership: groupMemberships) {
            GroupT group = groupTRepository.findByRef(groupMembership.getGroupRef());
            if(group == null)
                throw new VibentException(VibentError.GROUP_NOT_FOUND);
            groupTArrayList.add(group);
        }
        return groupTArrayList;
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
