package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.RankEntity;

public interface RankService {
    RankEntity createRank(String p_rankName);
    boolean rankExists(int p_id);
    RankEntity getRank(int p_id);
}
