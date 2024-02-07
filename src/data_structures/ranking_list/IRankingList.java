package data_structures.ranking_list;

import domain.Entry;

import java.util.List;

public interface IRankingList {
    void processParticipantEntry(Entry entry);
    List<Entry> getEntriesAsList();
}
