package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;

public interface AvailableCosmeticService {
    AvailableCosmeticEntity createCosmetic(String p_displayName, String p_category, boolean p_animated, Integer p_frames);

}
