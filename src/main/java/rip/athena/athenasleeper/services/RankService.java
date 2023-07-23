package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.RankEntity;

public interface RankService {
    void createRank(String p_rankName, String p_assetLocation);
    boolean rankExists(int p_id);
    RankEntity getRank(int p_id);
}
