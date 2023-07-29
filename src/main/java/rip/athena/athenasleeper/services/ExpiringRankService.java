package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.ExpiringRankEntity;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.entity.UserEntity;

public interface ExpiringRankService {
    boolean rankHasExpired(final UserEntity p_userEntity);
    ExpiringRankEntity findAndRemove(final UserEntity p_userEntity);

    /**
     * Add expiration to a user's current rank
     * @param p_userEntity The target user
     * @param p_timestampToRemove The UNIX timestamp in the future in which the rank should be removed
     * @param p_rankEntity The rank the user should receive after the time is up
     */
    void addExpirationToRank(final UserEntity p_userEntity, final long p_timestampToRemove, final RankEntity p_rankEntity);
}
