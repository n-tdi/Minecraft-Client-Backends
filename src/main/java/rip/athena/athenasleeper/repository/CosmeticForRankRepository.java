package rip.athena.athenasleeper.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rip.athena.athenasleeper.entity.CosmeticForRankEntity;
import rip.athena.athenasleeper.entity.RankEntity;

import java.util.List;

@Repository
public interface CosmeticForRankRepository extends JpaRepository<CosmeticForRankEntity, Integer> {
    List<CosmeticForRankEntity> findAllByRankEntity(RankEntity p_rankEntity);
    @Transactional
    void deleteAllByRankEntity(RankEntity p_rankEntity);
}
