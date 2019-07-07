package com.vibent.vibentback.distributionlist;

import com.vibent.vibentback.common.api.MailInviteIdRequest;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
import com.vibent.vibentback.common.mail.MailService;
import com.vibent.vibentback.common.util.TokenInfo;
import com.vibent.vibentback.common.util.TokenUtils;
import com.vibent.vibentback.distributionlist.api.DistributionListRequest;
import com.vibent.vibentback.distributionlist.api.DistributionListUpdateRequest;
import com.vibent.vibentback.distributionlist.membership.DistributionListMembership;
import com.vibent.vibentback.distributionlist.membership.DistributionListMembershipRepository;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.event.participation.EventParticipationService;
import com.vibent.vibentback.user.ConnectedUserUtils;
import com.vibent.vibentback.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DistributionListService {

    private final ConnectedUserUtils connectedUserUtils;
    private final EventRepository eventRepository;
    private final DistributionListRepository distributionListRepository;
    private final DistributionListMembershipRepository distributionListMembershipRepository;

    private final MailService mailService;
    private final EventParticipationService eventParticipationService;

    private final TokenUtils tokenUtils;

    public Set<DistributionList> getConnectedDistributionLists() {
        return connectedUserUtils.getConnectedUser()
                .getDistributionListMemberships().stream()
                .map(DistributionListMembership::getDistributionList)
                .collect(Collectors.toSet());
    }

    public DistributionList getDistributionList(long id) {
        return distributionListRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.DISTRIBUTION_LIST_NOT_FOUND));
    }

    public DistributionList createDistributionList(DistributionListRequest request) {
        Event event = eventRepository.findByRef(request.getEventRef())
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        DistributionList created = new DistributionList();
        created.setCreator(connectedUserUtils.getConnectedUser());
        created.setDescription(request.getDescription());
        created.setTitle(request.getTitle());
        created.getEvents().add(event);

        event.getParticipations().forEach(p -> {
                    DistributionListMembership membership = new DistributionListMembership();
                    membership.setUser(p.getUser());
                    membership.setDistributionList(created);
                    created.getMemberships().add(membership);
                }
        );

        return distributionListRepository.save(created);
    }

    public DistributionList updateDistributionList(Long id, DistributionListUpdateRequest request) {
        DistributionList distributionList = distributionListRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.DISTRIBUTION_LIST_NOT_FOUND));
        if (request.getTitle() != null) {
            distributionList.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            distributionList.setDescription(request.getDescription());
        }
        return distributionListRepository.save(distributionList);
    }

    public void deleteDistributionList(long id) {
        distributionListRepository.deleteById(id);
    }

    public void leaveDistributionList(Long id) {
        DistributionList distributionList = distributionListRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.DISTRIBUTION_LIST_NOT_FOUND));
        User connectedUser = connectedUserUtils.getConnectedUser();

        if (distributionList.getCreator().equals(connectedUser)) {
            throw new VibentException(VibentError.DISTRIBUTION_LIST_CANT_LEAVE_IF_CREATOR);
        }

        DistributionListMembership membership = distributionListMembershipRepository.findByUserAndDistributionList(connectedUser, distributionList)
                .orElseThrow(() -> new VibentException(VibentError.DISTRIBUTION_LIST_MEMBERSHIP_NOT_FOUND));


        distributionListMembershipRepository.delete(membership);
        distributionList.getMemberships().remove(membership);
        connectedUser.getDistributionListMemberships().remove(membership);
        distributionListRepository.save(distributionList);
        connectedUserUtils.updateConnectedUser();
    }

    public String generateDistributionListInviteToken(Long id) {
        return tokenUtils.getDistributionListInviteToken(id);
    }

    public void sendMailInvite(MailInviteIdRequest request) {
        DistributionList list = distributionListRepository.findById(request.getId())
                .orElseThrow(() -> new VibentException(VibentError.DISTRIBUTION_LIST_NOT_FOUND));
        User inviter = connectedUserUtils.getConnectedUser();
        mailService.sendDistributionListInviteMail(inviter, list, request.getRecipients());
    }

    public DistributionList validateInviteToken(String token) {
        TokenInfo info = tokenUtils.readDistributionListInviteToken(token);
        DistributionList list = distributionListRepository.findById(info.getId())
                .orElseThrow(() -> new VibentException(VibentError.DISTRIBUTION_LIST_NOT_FOUND));
        User connectedUser = connectedUserUtils.getConnectedUser();

        DistributionListMembership membership = new DistributionListMembership();
        membership.setDistributionList(list);
        membership.setUser(connectedUser);

        //Create event participations
        eventParticipationService.createEventParticipations(list.getEvents(), connectedUser);

        list.getMemberships().add(membership);
        return distributionListRepository.save(list);
    }
}
