package rip.athena.athenasleeper.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.CosmeticForRankEntity;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.repository.CosmeticForRankRepository;

import java.util.ArrayList;
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

    @Override
    public void addCosmeticToRank(final RankEntity p_rankEntity, final AvailableCosmeticEntity p_availableCosmeticEntity) {
        final CosmeticForRankEntity cosmeticForRankEntity = new CosmeticForRankEntity();
        cosmeticForRankEntity.setRankEntity(p_rankEntity);
        cosmeticForRankEntity.setAvailableCosmeticEntity(p_availableCosmeticEntity);

        m_cosmeticForRankRepository.save(cosmeticForRankEntity);
    }

    @Override
    public void removeCosmeticFromRank(final RankEntity p_rankEntity, final AvailableCosmeticEntity p_availableCosmeticEntity) {
        final List<CosmeticForRankEntity> removableEntities = new ArrayList<>();

        for (final CosmeticForRankEntity cosmeticForRankEntity : m_cosmeticForRankRepository.findAllByRankEntity(p_rankEntity)) {
            if (cosmeticForRankEntity.getAvailableCosmeticEntity().getId() == p_availableCosmeticEntity.getId()) {
                removableEntities.add(cosmeticForRankEntity);
            }
        }

        m_cosmeticForRankRepository.deleteAll(removableEntities);
    }
}
