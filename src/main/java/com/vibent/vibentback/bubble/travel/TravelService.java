package com.vibent.vibentback.bubble.travel;

import com.vibent.vibentback.bubble.travel.api.*;
import com.vibent.vibentback.user.ConnectedUserUtils;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposal;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposalRepository;
import com.vibent.vibentback.bubble.travel.request.TravelRequest;
import com.vibent.vibentback.bubble.travel.request.TravelRequestRepository;
import com.vibent.vibentback.common.error.VibentError;
import com.vibent.vibentback.common.error.VibentException;
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
    ConnectedUserUtils userUtils;

    // Travel Bubble -------------------------------------------------------------
    public TravelBubble getBubble(long id) {
        return bubbleRepository.findById(id).orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));
    }

    public TravelBubble createBubble(String eventRef) {
        Event event = eventRepository.findByRef(eventRef)
                .orElseThrow(() -> new VibentException(VibentError.EVENT_NOT_FOUND));
        TravelBubble travelBubble = new TravelBubble();
        travelBubble.setEvent(event);
        travelBubble.setCreator(userUtils.getConnectedUser());
        travelBubble.setDeleted(false);
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
        boolean proposalAlreadyExists = bubble.getProposals().stream().anyMatch(
                p -> p.getUser().equals(userUtils.getConnectedUser()));
        if (proposalAlreadyExists) {
            throw new VibentException(VibentError.TRAVEL_PROPOSAL_ALREADY_EXISTS);
        }
        TravelProposal proposal = new TravelProposal();
        proposal.setBubble(bubble);
        proposal.setCapacity(request.getCapacity());
        proposal.setPassByCities(request.getPassByCities());
        proposal.setDeleted(false);
        proposal.setUser(userUtils.getConnectedUser());
        bubble.getProposals().add(proposal);
        return bubbleRepository.save(bubble);
    }

    public TravelBubble updateProposal(Long id, TravelProposalUpdateRequest request) {
        TravelProposal proposal = proposalRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.TRAVEL_PROPOSAL_NOT_FOUND));
        if (request.getCapacity() != null)
            proposal.setCapacity(request.getCapacity());
        if (request.getPassByCities() != null)
            proposal.setPassByCities(request.getPassByCities());
        proposalRepository.save(proposal);
        return proposal.getBubble();
    }

    public void deleteProposal(Long id) {
        TravelProposal proposal = proposalRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.TRAVEL_PROPOSAL_NOT_FOUND));
        TravelBubble bubble = proposal.getBubble();
        bubble.getRequests().addAll(proposal.getAttachedRequests());
        bubble.getProposals().remove(proposal);
        proposalRepository.delete(proposal);
        bubbleRepository.save(bubble);
    }

    // Travel Request -------------------------------------------------------------
    public TravelBubble createRequest(TravelRequestRequest request) {
        TravelBubble bubble = bubbleRepository.findById(request.getBubbleId())
                .orElseThrow(() -> new VibentException(VibentError.BUBBLE_NOT_FOUND));

        boolean requestAlreadyExists = bubble.getRequests().stream().anyMatch(
                r -> r.getUser().equals(userUtils.getConnectedUser()));
        boolean attachedRequestAlreadyExists = bubble.getProposals().stream().anyMatch(
                p -> p.getAttachedRequests().stream().anyMatch(r -> r.getUser().equals(userUtils.getConnectedUser()))
        );
        if (requestAlreadyExists || attachedRequestAlreadyExists) {
            throw new VibentException(VibentError.TRAVEL_REQUEST_ALREADY_EXISTS);
        }

        TravelRequest travelRequest = new TravelRequest();
        travelRequest.setBubble(bubble);
        travelRequest.setCapacity(request.getCapacity());
        travelRequest.setPassByCities(request.getPassByCities());
        travelRequest.setDeleted(false);
        travelRequest.setUser(userUtils.getConnectedUser());
        bubble.getRequests().add(travelRequest);
        return bubbleRepository.save(bubble);
    }

    public TravelBubble createRequestAndAttach(TravelRequestAndAttachRequest request) {
        TravelProposal proposal = proposalRepository.findById(request.getProposalId())
                .orElseThrow(() -> new VibentException(VibentError.TRAVEL_PROPOSAL_NOT_FOUND));
        TravelBubble bubble = proposal.getBubble();

        boolean requestAlreadyExists = bubble.getRequests().stream().anyMatch(
                r -> r.getUser().equals(userUtils.getConnectedUser()));
        boolean attachedRequestAlreadyExists = bubble.getProposals().stream().anyMatch(
                p -> p.getAttachedRequests().stream().anyMatch(r -> r.getUser().equals(userUtils.getConnectedUser()))
        );
        if (requestAlreadyExists || attachedRequestAlreadyExists) {
            throw new VibentException(VibentError.TRAVEL_REQUEST_ALREADY_EXISTS);
        }

        int usedCapacity = proposal.getAttachedRequests().stream().mapToInt(TravelRequest::getCapacity).sum();
        if (usedCapacity + request.getCapacity() > proposal.getCapacity()) {
            throw new VibentException(VibentError.TRAVEL_PROPOSAL_CAPACITY_SURPASSED);
        }

        TravelRequest travelRequest = new TravelRequest();
        travelRequest.setProposal(proposal);
        travelRequest.setCapacity(request.getCapacity());
        travelRequest.setPassByCities(request.getPassByCities());
        travelRequest.setDeleted(false);
        travelRequest.setUser(userUtils.getConnectedUser());
        proposal.getAttachedRequests().add(travelRequest);
        proposalRepository.save(proposal);
        return bubble;
    }

    public TravelBubble updateRequest(Long id, TravelRequestUpdateRequest request) {
        TravelRequest travelRequest = requestRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.TRAVEL_REQUEST_NOT_FOUND));
        if (request.getCapacity() != null)
            travelRequest.setCapacity(request.getCapacity());
        if (request.getPassByCities() != null)
            travelRequest.setPassByCities(request.getPassByCities());
        requestRepository.save(travelRequest);
        return travelRequest.getBubble();
    }

    public void deleteRequest(Long id) {
        TravelRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new VibentException(VibentError.OPTION_NOT_FOUND));
        TravelBubble bubble = request.getBubble();
        TravelProposal proposal = request.getProposal();
        // If the request is attached to a bubble
        if (bubble != null) {
            bubble.getRequests().remove(request);
            bubbleRepository.save(bubble);
        }
        // If request is attached to a proposal
        if (proposal != null) {
            proposal.getAttachedRequests().remove(request);
            proposalRepository.save(proposal);
        }
        requestRepository.delete(request);
    }

    // Attach / Detach
    public TravelBubble attach(TravelAttachRequest request) {
        TravelRequest travelRequest = requestRepository.findById(request.getRequestId())
                .orElseThrow(() -> new VibentException(VibentError.TRAVEL_REQUEST_NOT_FOUND));
        TravelProposal proposal = proposalRepository.findById(request.getProposalId())
                .orElseThrow(() -> new VibentException(VibentError.TRAVEL_PROPOSAL_NOT_FOUND));
        
        TravelBubble bubble = travelRequest.getBubble();
        if (bubble == null || !bubble.equals(proposal.getBubble())) {
            throw new VibentException(VibentError.TRAVEL_REQUEST_AND_PROPOSAL_INCOMPATIBLE);
        }

        int usedCapacity = proposal.getAttachedRequests().stream().mapToInt(TravelRequest::getCapacity).sum();
        if (usedCapacity + travelRequest.getCapacity() > proposal.getCapacity()) {
            throw new VibentException(VibentError.TRAVEL_PROPOSAL_CAPACITY_SURPASSED);
        }

        bubble.getRequests().remove(travelRequest);
        proposal.getAttachedRequests().add(travelRequest);
        travelRequest.setBubble(null);
        travelRequest.setProposal(proposal);
        requestRepository.save(travelRequest);
        proposalRepository.save(proposal);
        return bubbleRepository.save(bubble);
    }

    public TravelBubble detach(TravelAttachRequest request) {
        TravelRequest travelRequest = requestRepository.findById(request.getRequestId())
                .orElseThrow(() -> new VibentException(VibentError.TRAVEL_REQUEST_NOT_FOUND));
        TravelProposal proposal = proposalRepository.findById(request.getProposalId())
                .orElseThrow(() -> new VibentException(VibentError.TRAVEL_PROPOSAL_NOT_FOUND));
        if (!proposal.getAttachedRequests().contains(travelRequest)) {
            throw new VibentException(VibentError.TRAVEL_REQUEST_AND_PROPOSAL_INCOMPATIBLE);
        }
        TravelBubble bubble = proposal.getBubble();
        bubble.getRequests().add(travelRequest);
        proposal.getAttachedRequests().remove(travelRequest);
        travelRequest.setBubble(bubble);
        travelRequest.setProposal(null);
        requestRepository.save(travelRequest);
        proposalRepository.save(proposal);
        return bubbleRepository.save(bubble);
    }
}
