package rip.athena.athenasleeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rip.athena.athenasleeper.entity.OwnedCosmeticEntity;
import rip.athena.athenasleeper.entity.UserEntity;

import java.util.UUID;

@Repository
public interface OwnedCosmeticRepository extends JpaRepository<OwnedCosmeticEntity, UUID> {
}
