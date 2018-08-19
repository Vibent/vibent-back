package com.vibent.vibentback.group.membership;

import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.event.participation.EventParticipation;
import com.vibent.vibentback.group.GroupT;
import com.vibent.vibentback.group.GroupTRepository;
import com.vibent.vibentback.user.User;
import com.vibent.vibentback.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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
        if (connectedUserUtils.getConnectedUser().getMemberships().stream().anyMatch(m -> m.getGroup().getRef().equals(groupRef))) {
            throw new VibentException(VibentError.USER_ALREADY_PART_OF_GROUP);
        }
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
        if (user.getMemberships().stream().anyMatch(m -> m.getGroup().getRef().equals(group.getRef())))
            throw new VibentException(VibentError.USER_ALREADY_PART_OF_GROUP);
        if (membershipRequestRepository.existsByUserAndGroup(user, group)){
            deleteMembershipRequest(group.getRef(), user.getRef());
        }
        if(group.isHasDefaultAdmin()){
            isAdmin = true;
        }

        // Link both entities
        Membership membership = new Membership(user, group, isAdmin);
        user.getMemberships().add(membership);
        group.getMemberships().add(membership);

        // Add a participation for all existing events
        group.getEvents().forEach(event -> event.getParticipations().add(new EventParticipation(user, event)));

        return membershipRepository.save(membership);
    }

    public Membership addMembership(String groupRef, String userRef, boolean isAdmin) {
        GroupT groupT = groupTRepository.findByRef(groupRef)
                .orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        User user = userRepository.findByRef(userRef)
                .orElseThrow(() -> new VibentException(VibentError.USER_NOT_FOUND));
        return addMembership(groupT, user, isAdmin);
    }

    public Membership changeAdminship(GroupT group, User user, boolean isAdmin) {
        Membership membership = membershipRepository.findByUserAndGroup(user, group)
                .orElseThrow(() -> new VibentException(VibentError.MEMBERSHIP_NOT_FOUND));
        if(group.isHasDefaultAdmin()){
            isAdmin = true;
        }
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

