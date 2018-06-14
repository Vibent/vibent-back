package com.vibent.vibentback.groupT.membership;

import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.groupT.GroupT;
import com.vibent.vibentback.groupT.GroupTRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MembershipService {

    private ConnectedUserUtils connectedUserUtils;
    private MembershipRepository membershipRepository;
    private MembershipRequestRepository membershipRequestRepository;
    private GroupTRepository groupTRepository;
    private UserRepository userRepository;

    public MembershipRequest addMembershipRequestForConnectedUser(String groupRef){
        GroupT groupT = groupTRepository.findByRef(groupRef)
                .orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        MembershipRequest created = new MembershipRequest(connectedUserUtils.getConnectedUser(), groupT);
        groupT.getRequests().add(created);
        connectedUserUtils.getConnectedUser().getRequests().add(created);
        return membershipRequestRepository.save(created);
    }

    public void deleteMembershipRequest(String groupRef, String userRef){
        GroupT groupT = groupTRepository.findByRef(groupRef)
                .orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        User user = userRepository.findByRef(userRef)
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        MembershipRequest request = membershipRequestRepository.findByUserAndGroup(user, groupT)
                .orElseThrow(() -> new VibentException(VibentError.MEMBERSHIP_REQUEST_NOT_FOUND));
        groupT.getRequests().remove(request);
        connectedUserUtils.getConnectedUser().getRequests().remove(request);
        membershipRequestRepository.delete(request);
    }

    public Membership addMembership(GroupT group, User user, boolean isAdmin) {
        Membership membership = new Membership(user, group, isAdmin);
        user.getMemberships().add(membership);
        group.getMemberships().add(membership);
        return membershipRepository.save(membership);
    }

    public Membership changeAdminship(GroupT group, User user, boolean isAdmin) {
        Membership membership = membershipRepository.findByUserAndGroup(user, group)
                .orElseThrow(() -> new VibentException(VibentError.MEMBERSHIP_NOT_FOUND));
        membership.setAdmin(isAdmin);
        return membershipRepository.save(membership);
    }

    public void removeMembership(GroupT group, User user){
        Membership membership = membershipRepository.findByUserAndGroup(user, group)
                .orElseThrow(() -> new VibentException(VibentError.MEMBERSHIP_NOT_FOUND));
        group.getMemberships().remove(membership);
        user.getMemberships().remove(membership);
        membershipRepository.deleteByUserAndGroup(user, group);
    }

    public void removeMembership(GroupT group){
        Set<Membership> memberships = membershipRepository.findByGroup(group);
        memberships.forEach(membership -> {
            membership.getUser().getMemberships().remove(membership);
        });
        group.getMemberships().removeIf(membership -> true);
        membershipRepository.deleteByGroup(group);
    }

    public void removeMembership(User user){
        Set<Membership> memberships = membershipRepository.findByUser(user);
        memberships.forEach(membership -> {
            membership.getGroup().getMemberships().remove(membership);
        });
        user.getMemberships().removeIf(membership -> true);
        membershipRepository.deleteByUser(user);
    }
}

