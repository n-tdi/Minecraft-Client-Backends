package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.entity.UserEntity;

import java.util.List;
import java.util.Set;

public interface CosmeticForRankService {
    List<AvailableCosmeticEntity> getAllCosmeticsFromRankOfUser(final UserEntity p_userEntity);
    void addCosmeticToRank(final RankEntity p_rankEntity, final AvailableCosmeticEntity p_availableCosmeticEntity);
    void removeCosmeticFromRank(final RankEntity p_rankEntity, final AvailableCosmeticEntity p_availableCosmeticEntity);
    void setCosmeticForRank(final RankEntity p_rankEntity, final Set<AvailableCosmeticEntity> p_availableCosmeticEntities);
}
