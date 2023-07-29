package rip.athena.athenasleeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rip.athena.athenasleeper.entity.ExpiringRankEntity;
import rip.athena.athenasleeper.entity.UserEntity;

@Repository
public interface ExpiringRankRepository extends JpaRepository<ExpiringRankEntity, UserEntity> {
}
