package rip.athena.athenasleeper.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.repository.AvailableCosmeticRepository;

@Service
@Slf4j
@AllArgsConstructor
public class AvailableCosmeticServiceImpl implements AvailableCosmeticService {
    private AvailableCosmeticRepository m_availableCosmeticRepository;

    @Override
    public int createCosmetic(final String p_displayName, final boolean p_animated, final Integer p_frames, final String p_assetLocation) {
        final AvailableCosmeticEntity availableCosmeticEntity = new AvailableCosmeticEntity();
        availableCosmeticEntity.setDisplayName(p_displayName);
        availableCosmeticEntity.setAnimated(p_animated);
        availableCosmeticEntity.setFrames(p_frames);
        availableCosmeticEntity.setAssetLocation(p_assetLocation);

        return m_availableCosmeticRepository.save(availableCosmeticEntity).getId();
    }
}
