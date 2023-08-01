package rip.athena.athenasleeper.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rip.athena.athenasleeper.entity.ExpiringRankEntity;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.repository.ExpiringRankRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class ExpiringRankServiceImpl implements ExpiringRankService {
    private ExpiringRankRepository m_expiringRankRepository;

    @Override
    public boolean rankHasExpired(final UserEntity p_userEntity) {
        if (m_expiringRankRepository.existsById(p_userEntity.getUuid())) {
            return true;
        }

        final ExpiringRankEntity expiringRankEntity = m_expiringRankRepository.findById(p_userEntity.getUuid()).orElseThrow();

        final long timestampRightNow = System.currentTimeMillis();

        return timestampRightNow > expiringRankEntity.getTimestampAtExpiration();
    }

    @Override
    public ExpiringRankEntity findAndRemove(final UserEntity p_userEntity) {
        if (m_expiringRankRepository.existsById(p_userEntity.getUuid())) {
            return null;
        }

        final ExpiringRankEntity expiringRankEntity = m_expiringRankRepository.findById(p_userEntity.getUuid()).orElseThrow();

        m_expiringRankRepository.deleteById(p_userEntity.getUuid());

        return expiringRankEntity;
    }

    @Override
    public LocalDate getExpiration(final UserEntity p_userEntity) {
        final ExpiringRankEntity expiringRankEntity = m_expiringRankRepository.findByUserEntityUuid(p_userEntity.getUuid());

        if (expiringRankEntity == null) {
            return null;
        }

        Instant instant = Instant.ofEpochMilli(expiringRankEntity.getTimestampAtExpiration());

        return instant.atOffset(ZoneOffset.UTC).toLocalDate();
    }

    @Override
    public void addExpirationToRank(final UserEntity p_userEntity, final long p_timestampToRemove, final RankEntity p_rankEntity) {
        final ExpiringRankEntity expiringRankEntity = new ExpiringRankEntity();
        expiringRankEntity.setUserEntityUuid(p_userEntity.getUuid());
        expiringRankEntity.setRankEntity(p_rankEntity);
        expiringRankEntity.setTimestampAtExpiration(p_timestampToRemove);
    }
}
