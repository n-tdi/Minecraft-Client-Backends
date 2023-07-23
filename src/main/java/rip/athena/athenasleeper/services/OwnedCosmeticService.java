package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.UserEntity;

public interface OwnedCosmeticService {
    void addCosmetic(UserEntity p_userEntity, AvailableCosmeticEntity p_availableCosmeticEntity);
    void removeCosmetic(UserEntity p_userEntity, AvailableCosmeticEntity p_availableCosmeticEntity);
}
