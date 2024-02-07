package data_structures.ranking_list;

import domain.Cursant;

import java.util.List;

public interface IRankingList {
    void processParticipantEntry(Cursant entry);
    List<Cursant> getEntriesAsList();
}
