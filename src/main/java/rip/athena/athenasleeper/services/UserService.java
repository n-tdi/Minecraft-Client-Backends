package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.model.ActiveInfo;
import rip.athena.athenasleeper.model.UserSession;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void userLogOn(UserSession p_userSession);
    void userLogOut(UserSession p_userSession);
    void insertIntoTable(UUID p_uuid);
    boolean existsInTable(UUID p_uuid);
    boolean ownsCosmetic(UUID p_uuid, AvailableCosmeticEntity p_availableCosmeticEntity);
    List<AvailableCosmeticEntity> getOwnedCosmetics(UUID p_uuid);
    RankEntity getRank(UUID p_uuid);
    List<ActiveInfo> getActiveInformation();
}
