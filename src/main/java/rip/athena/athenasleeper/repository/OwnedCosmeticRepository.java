package rip.athena.athenasleeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.OwnedCosmeticEntity;
import rip.athena.athenasleeper.entity.UserEntity;

import java.util.List;

@Repository
public interface OwnedCosmeticRepository extends JpaRepository<OwnedCosmeticEntity, Integer> {
    List<OwnedCosmeticEntity> findAllByUserEntity_Uuid(String p_uuid);
    void deleteAllByUserEntityAndAvailableCosmeticEntity(UserEntity p_userEntity, AvailableCosmeticEntity p_availableCosmeticEntity);
    void deleteAllByUserEntity(UserEntity p_userEntity);
    long count();
}
