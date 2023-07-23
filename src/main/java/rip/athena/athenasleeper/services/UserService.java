package rip.athena.athenasleeper.services;

import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.model.ActiveInfo;
import rip.athena.athenasleeper.model.UserSession;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void userLogOn(UserSession p_userSession);
    void userLogOut(UserSession p_userSession);
    void insertIntoTable(String p_uuid);
    boolean existsInTable(String p_uuid);
    boolean ownsCosmetic(String p_uuid, AvailableCosmeticEntity p_availableCosmeticEntity);
    List<AvailableCosmeticEntity> getOwnedCosmetics(String p_uuid);
    RankEntity getRank(String p_uuid);
    List<ActiveInfo> getActiveInformation();
    ActiveInfo getActiveInformation(UserEntity p_userEntity, String p_type);
}
