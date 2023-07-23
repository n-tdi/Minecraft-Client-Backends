package rip.athena.athenasleeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.OwnedCosmeticEntity;
import rip.athena.athenasleeper.entity.UserEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface OwnedCosmeticRepository extends JpaRepository<OwnedCosmeticEntity, Integer> {
    List<OwnedCosmeticEntity> findAllByUserEntity_Uuid(UUID p_uuid);
}
