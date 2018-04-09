package com.vibent.vibentback.bubble.travel;

import com.vibent.vibentback.Mock;
import com.vibent.vibentback.api.alimentation.AlimentationBringRequest;
import com.vibent.vibentback.api.alimentation.AlimentationBringUpdateRequest;
import com.vibent.vibentback.api.alimentation.AlimentationEntryRequest;
import com.vibent.vibentback.api.alimentation.AlimentationEntryUpdateRequest;
import com.vibent.vibentback.api.travel.TravelProposalRequest;
import com.vibent.vibentback.api.travel.TravelProposalUpdateRequest;
import com.vibent.vibentback.api.travel.TravelRequestRequest;
import com.vibent.vibentback.api.travel.TravelRequestUpdateRequest;
import com.vibent.vibentback.bubble.BubbleType;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.AlimentationBubbleRepository;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBringRepository;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntryRepository;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposal;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposalRepository;
import com.vibent.vibentback.bubble.travel.request.TravelRequest;
import com.vibent.vibentback.bubble.travel.request.TravelRequestRepository;
import com.vibent.vibentback.common.ObjectUpdater;
import com.vibent.vibentback.error.VibentError;
import com.vibent.vibentback.error.VibentException;
import com.vibent.vibentback.event.Event;
import com.vibent.vibentback.event.EventRepository;
import com.vibent.vibentback.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TravelService {

    TravelBubbleRepository bubbleRepository;
    TravelRequestRepository requestRepository;
    TravelProposalRepository proposalRepository;
    EventRepository eventRepository;
    UserRepository userRepository;

    // Travel Bubble -------------------------------------------------------------
    public TravelBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public TravelBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        TravelBubble travelBubble = new TravelBubble();
        travelBubble.setEvent(event);
        travelBubble.setCreator(Mock.getConnectedUser(userRepository));
        travelBubble.setDeleted(false);
        travelBubble.setType(BubbleType.TravelBubble);
        travelBubble = bubbleRepository.save(travelBubble);
        return travelBubble;
    }

    public void deleteBubble(long id) {
        bubbleRepository.deleteById(id);
    }

    // Travel Proposal -------------------------------------------------------------
    public TravelBubble createProposal(TravelProposalRequest request) {
        TravelBubble bubble = bubbleRepository.findById(request.getBubbleId())
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
        TravelProposal proposal = new TravelProposal();
        proposal.setBubble(bubble);
        proposal.setCapacity(request.getCapacity());
        proposal.setPassByCities(request.getPassByCities());
        proposal.setDeleted(false);
        proposalRepository.save(proposal);
        return bubble;
    }

    public TravelBubble updateProposal(Long id, TravelProposalUpdateRequest request) {
        TravelProposal proposal = proposalRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.PROPOSAL_NOT_FOUND));
        ObjectUpdater.updateProperties(request, proposal);
        proposalRepository.save(proposal);
        return proposal.getBubble();
    }

    public void deleteProposal(Long id) {
        proposalRepository.deleteById(id);
    }

    // Travel request -------------------------------------------------------------
    public TravelBubble createRequest(TravelRequestRequest request) {
        TravelBubble bubble = bubbleRepository.findById(request.getBubbleId())
                .orElseThrow(() -> new VibentException(VibentError.REQUEST_NOT_FOUND));
        TravelRequest travelRequest = new TravelRequest();
        travelRequest.setCapacity(request.getCapacity());
        travelRequest.setUser(Mock.getConnectedUser(userRepository));
        travelRequest.setIsAttachedToProposal(request.getIsAttachedToProposal());
        travelRequest.setDeleted(false);
        requestRepository.save(travelRequest);
        return bubble;
    }

    public TravelBubble updateRequest(Long id, TravelRequestUpdateRequest request) {
        TravelRequest travelRequest = requestRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.REQUEST_NOT_FOUND));
        travelRequest.setIsAttachedToProposal(request.getIsAttachedToProposal());
        travelRequest.setCapacity(request.getCapacity());
        return travelRequest.getBubble();
    }

    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }
}
