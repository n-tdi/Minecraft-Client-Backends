package rip.athena.athenasleeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.UserEntity;

import java.util.UUID;

@Repository
public interface AvailableCosmeticRepository extends JpaRepository<AvailableCosmeticEntity, Integer> {
}
