package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.ExpiringRankEntity;
import rip.athena.athenasleeper.entity.UserEntity;

public interface ExpiringRankService {
    boolean rankHasExpired(final UserEntity p_userEntity);
    ExpiringRankEntity findAndRemove(final UserEntity p_userEntity);
}
