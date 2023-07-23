package rip.athena.athenasleeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rip.athena.athenasleeper.entity.RankEntity;

public interface RankRepository extends JpaRepository<RankEntity, Integer> {
}
