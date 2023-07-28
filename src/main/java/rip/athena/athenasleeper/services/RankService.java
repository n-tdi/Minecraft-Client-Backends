package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.RankEntity;

public interface RankService {
    void createRank(String p_rankName);
    boolean rankExists(int p_id);
    RankEntity getRank(int p_id);
}
