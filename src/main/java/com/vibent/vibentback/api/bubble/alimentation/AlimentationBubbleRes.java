package com.vibent.vibentback.api.bubble.alimentation;

import com.vibent.vibentback.bubble.alimentation.AlimentationBubble;
import com.vibent.vibentback.bubble.alimentation.bring.AlimentationBring;
import com.vibent.vibentback.bubble.alimentation.entry.AlimentationEntry;
import com.vibent.vibentback.common.ObjectUpdater;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class AlimentationBubbleRes extends AlimentationBubble {

    public AlimentationBubbleRes(AlimentationBubble bubble) {
        super();
        ObjectUpdater.updateAllProperties(bubble, this);
        entries = new ArrayList<>();
    }

    public void addEntry(AlimentationEntry entry, Iterable<AlimentationBring> brings) {
        AlimentationEntryRes res = new AlimentationEntryRes(entry);
        brings.forEach(res::addBring);
        entries.add(res);
    }

    ArrayList<AlimentationEntryRes> entries;

    @Getter
    @Setter
    private class AlimentationEntryRes extends AlimentationEntry {
        ArrayList<AlimentationBringRes> brings;

        public AlimentationEntryRes(AlimentationEntry entry) {
            super();
            ObjectUpdater.updateAllProperties(entry, this);
            brings = new ArrayList<>();
        }

        public void addBring(AlimentationBring bring) {
            brings.add(new AlimentationBringRes(bring));
        }

        @Setter
        @Getter
        private class AlimentationBringRes extends AlimentationBring {
            public AlimentationBringRes(AlimentationBring bring) {
                super();
                ObjectUpdater.updateAllProperties(bring, this);
            }
        }
    }
}
