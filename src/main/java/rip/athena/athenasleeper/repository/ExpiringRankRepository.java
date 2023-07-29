package rip.athena.athenasleeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rip.athena.athenasleeper.entity.ExpiringRankEntity;

@Repository
public interface ExpiringRankRepository extends JpaRepository<ExpiringRankEntity, String  > {
    ExpiringRankEntity findByUserEntityUuid(String p_userEntityUuid);
}
