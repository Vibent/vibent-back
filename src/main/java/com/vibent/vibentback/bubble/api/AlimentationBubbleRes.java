package com.vibent.vibentback.bubble.api;

import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.common.ObjectUpdater;
import lombok.Data;

import java.util.ArrayList;

@Data
public class AlimentationBubbleRes extends AlimentationBubble {

    public AlimentationBubbleRes (AlimentationBubble bubble){
        ObjectUpdater.updateProperties(bubble, this);
        entries = new ArrayList<>();
    }

    public void addEntry(AlimentationEntry entry, Iterable<AlimentationBring> brings) {
        AlimentationEntryRes res = new AlimentationEntryRes(entry);
        for(AlimentationBring bring : brings){
            res.addBring(bring);
        }
        entries.add(res);
    }

    ArrayList<AlimentationEntryRes> entries;

    @Data
    private class AlimentationEntryRes extends AlimentationEntry {
        ArrayList<AlimentationBringRes> brings;

        public AlimentationEntryRes(AlimentationEntry entry){
            ObjectUpdater.updateProperties(entry, this);
            brings = new ArrayList<>();
        }

        public void addBring(AlimentationBring bring){
            brings.add(new AlimentationBringRes(bring));
        }

        @Data
        private class AlimentationBringRes extends AlimentationBring {
            public AlimentationBringRes (AlimentationBring bring){
                ObjectUpdater.updateProperties(bring, this);
            }
        }
    }
}
