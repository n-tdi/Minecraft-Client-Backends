package rip.athena.athenasleeper.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.CosmeticForRankEntity;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.repository.CosmeticForRankRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CosmeticForRankServiceImpl implements CosmeticForRankService {
    private CosmeticForRankRepository m_cosmeticForRankRepository;

    @Override
    public List<AvailableCosmeticEntity> getAllCosmeticsFromRankOfUser(final UserEntity p_userEntity) {
        final RankEntity rankEntity = p_userEntity.getRankEntity();

        final List<CosmeticForRankEntity> cosmeticForRankEntities = m_cosmeticForRankRepository.findAllByRankEntity(rankEntity);

        return cosmeticForRankEntities
                .stream()
                .map(CosmeticForRankEntity::getAvailableCosmeticEntity)
                .toList();
    }
}
