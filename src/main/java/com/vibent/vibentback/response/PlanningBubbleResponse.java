package com.vibent.vibentback.response;

import com.vibent.vibentback.bubble.planning.PlanningBubble;
import com.vibent.vibentback.bubble.planning.entry.PlanningEntry;
import lombok.Data;

import java.util.ArrayList;

@Data
public class PlanningBubbleResponse {
    private PlanningBubble planningBubble;
    private ArrayList<PlanningEntry> planningEntryResponses;
}
