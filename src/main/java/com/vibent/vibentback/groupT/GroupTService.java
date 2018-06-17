package com.vibent.vibentback.groupT;

import com.vibent.vibentback.ConnectedUserUtils;
import com.vibent.vibentback.api.groupT.GroupRequest;
import com.vibent.vibentback.api.groupT.GroupUpdateRequest;
import com.vibent.vibentback.api.groupT.ValidateInviteTokenRequest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.util.TokenUtils;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.groupT.membership.Membership;
import com.vibent.vibentback.groupT.membership.MembershipService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GroupTService {

    MembershipService membershipService;
    ConnectedUserUtils connectedUserUtils;
    GroupTRepository groupTRepository;
    TokenUtils tokenUtils;

    public Set<GroupT> getConnectedUserGroups() {
        return connectedUserUtils.getConnectedUser().getMemberships().stream().map(Membership::getGroup).collect(Collectors.toSet());
    }

    public Set<Membership> getConnectedUserMemberships() {
        return connectedUserUtils.getConnectedUser().getMemberships();
    }

    public GroupT getGroupT(String groupRef) {
        return groupTRepository.findByRef(groupRef)
                .orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
    }

    public GroupT getPublicGroupInfo(String groupRef) {
        return groupTRepository.findByRef(groupRef)
                .orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
    }

    public Set<Event> getGroupEvents(String groupRef) {
        GroupT group = groupTRepository.findByRef(groupRef)
                .orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        return group.getEvents();
    }

    public GroupT createGroupT(GroupRequest request) {
        GroupT group = new GroupT();
        group.setRef(UUID.randomUUID().toString());
        group.setName(request.getName());
        group.setDescription(request.getDescription());
        group.setHasDefaultAdmin(request.getAllAdmins());
        groupTRepository.save(group);
        membershipService.addMembership(group, connectedUserUtils.getConnectedUser(), true);
        return group;
    }

    public void deleteGroupT(String groupRef) {
        groupTRepository.deleteByRef(groupRef);
    }

    public GroupT updateGroupT(String groupRef, GroupUpdateRequest request) {
        GroupT existing = groupTRepository.findByRef(groupRef)
                .orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        if (request.getName() != null) existing.setName(request.getName());
        if (request.getDescription() != null) {
            if (request.getDescription().isEmpty())
                existing.setDescription(null);
            else
                existing.setDescription(request.getDescription());
        }
        if (request.getAllAdmins() != null) existing.setHasDefaultAdmin(request.getAllAdmins());
        return groupTRepository.save(existing);
    }

    public String generateInviteToken(String groupRef) {
        return tokenUtils.createGroupInviteToken(groupRef);
    }

    public GroupT validateInviteToken(ValidateInviteTokenRequest request) {
        Claims claims = tokenUtils.validateJWTToken(request.getToken());
        String groupRef = claims.getSubject();
        GroupT groupT = groupTRepository.findByRef(groupRef)
                .orElseThrow(() -> new VibentException(VibentError.GROUP_NOT_FOUND));
        membershipService.addMembership(groupT, connectedUserUtils.getConnectedUser(), false);
        return groupT;
    }
}
