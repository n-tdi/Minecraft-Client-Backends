package rip.athena.athenasleeper.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.repository.RankRepository;

@Service
@Slf4j
@AllArgsConstructor
public class RankServiceImpl implements RankService{
    private RankRepository m_rankRepository;

    @Override
    public RankEntity createRank(final String p_rankName) {
        final RankEntity rankEntity = new RankEntity();
        rankEntity.setRankName(p_rankName);
        log.info("Created {} as a new rank", p_rankName);
        return m_rankRepository.save(rankEntity);
    }

    @Override
    public boolean rankExists(final int p_id) {
        return m_rankRepository.existsById(p_id);
    }

    @Override
    public RankEntity getRank(final int p_id) {
        return m_rankRepository.findById(p_id).orElseThrow();
    }
}
