package rip.athena.athenasleeper.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.OwnedCosmeticEntity;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.repository.OwnedCosmeticRepository;

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
    public long countAllOwnedCosmetics() {
        return m_ownedCosmeticRepository.count();
    }
}
