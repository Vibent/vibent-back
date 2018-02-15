package com.vibent.vibentback.response;

import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.bringing.AlimentationBringing;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import lombok.Data;

import java.util.ArrayList;

@Data
public class AlimentationBubbleResponse {
    private AlimentationBubble alimentationBubble;
    private ArrayList<AlimentationEntry> alimentationEntries;
    private ArrayList<AlimentationBringing> alimentationBringings;
}
