package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;

public interface AvailableCosmeticService {
    AvailableCosmeticEntity createCosmetic(String p_displayName, boolean p_animated, Integer p_frames);

}
