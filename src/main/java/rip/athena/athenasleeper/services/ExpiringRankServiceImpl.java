package rip.athena.athenasleeper.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rip.athena.athenasleeper.entity.ExpiringRankEntity;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.repository.ExpiringRankRepository;

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
}
