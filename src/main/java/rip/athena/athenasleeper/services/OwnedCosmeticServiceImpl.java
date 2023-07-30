package rip.athena.athenasleeper.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rip.athena.athenasleeper.entity.*;
import rip.athena.athenasleeper.repository.OwnedCosmeticRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class OwnedCosmeticServiceImpl implements OwnedCosmeticService{
    private OwnedCosmeticRepository m_ownedCosmeticRepository;

    @Override
    public void addCosmetic(final UserEntity p_userEntity, final AvailableCosmeticEntity p_availableCosmeticEntity) {
        final OwnedCosmeticEntity ownedCosmeticEntity = new OwnedCosmeticEntity();
        ownedCosmeticEntity.setUserEntity(p_userEntity);
        ownedCosmeticEntity.setAvailableCosmeticEntity(p_availableCosmeticEntity);
        m_ownedCosmeticRepository.save(ownedCosmeticEntity);
    }

    @Override
    public void removeCosmetic(final UserEntity p_userEntity, final AvailableCosmeticEntity p_availableCosmeticEntity) {
        m_ownedCosmeticRepository.deleteAllByUserEntityAndAvailableCosmeticEntity(p_userEntity, p_availableCosmeticEntity);
    }

    @Override
    public void setCosmetic(final UserEntity p_userEntity, final Set<AvailableCosmeticEntity> p_availableCosmeticEntities) {
        m_ownedCosmeticRepository.deleteAllByUserEntity(p_userEntity);

        final List<OwnedCosmeticEntity> ownedCosmeticEntities = new ArrayList<>();

        for (final AvailableCosmeticEntity availableCosmeticEntity : p_availableCosmeticEntities) {
            final OwnedCosmeticEntity ownedCosmeticEntity = new OwnedCosmeticEntity();
            ownedCosmeticEntity.setUserEntity(p_userEntity);
            ownedCosmeticEntity.setAvailableCosmeticEntity(availableCosmeticEntity);

            ownedCosmeticEntities.add(ownedCosmeticEntity);
        }

        m_ownedCosmeticRepository.saveAll(ownedCosmeticEntities);
    }

    @Override
    public long countAllOwnedCosmetics() {
        return m_ownedCosmeticRepository.count();
    }
}
