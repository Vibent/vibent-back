package com.vibent.vibentback.response;

import com.vibent.vibentback.bubble.travel.TravelBubble;
import com.vibent.vibentback.bubble.travel.proposal.TravelProposal;
import com.vibent.vibentback.bubble.travel.request.TravelRequest;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TravelBubbleResponse {
    private TravelBubble travelBubble;
    private ArrayList<TravelProposal> travelProposals;
    private ArrayList<TravelRequest> travelRequests;
}
