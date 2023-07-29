package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.UserEntity;

import java.util.List;

public interface CosmeticForRankService {
    List<AvailableCosmeticEntity> getAllCosmeticsFromRankOfUser(final UserEntity p_userEntity);
}
