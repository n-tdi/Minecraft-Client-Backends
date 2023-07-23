package rip.athena.athenasleeper.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rip.athena.athenasleeper.AthenaSleeperApplication;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.OwnedCosmeticEntity;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.model.ActiveInfo;
import rip.athena.athenasleeper.model.UserSession;
import rip.athena.athenasleeper.repository.AvailableCosmeticRepository;
import rip.athena.athenasleeper.repository.OwnedCosmeticRepository;
import rip.athena.athenasleeper.repository.RankRepository;
import rip.athena.athenasleeper.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository m_userRepository;
    private RankRepository m_rankRepository;
    private OwnedCosmeticRepository m_ownedCosmeticRepository;
    private AvailableCosmeticRepository m_availableCosmeticRepository;

    @Override
    public void userLogOn(final UserSession p_userSession) {
        if (!existsInTable(p_userSession.getUuid())) {
            insertIntoTable(p_userSession.getUuid());
        }

        final UserEntity userEntity = m_userRepository.findById(p_userSession.getUuid()).orElseThrow();
        userEntity.setOnline(true);
        m_userRepository.save(userEntity);
    }

    @Override
    public void userLogOut(final UserSession p_userSession) {
        if (!existsInTable(p_userSession.getUuid())) {
            return;
        }

        final UserEntity userEntity = m_userRepository.findById(p_userSession.getUuid()).orElseThrow();
        userEntity.setOnline(false);
        m_userRepository.save(userEntity);
    }

    @Override
    public void insertIntoTable(final String p_uuid) {
        final UserEntity userEntity = new UserEntity();

        userEntity.setUuid(p_uuid);
        userEntity.setOnline(false);
        userEntity.setRankEntity(m_rankRepository.findById(1).orElseThrow());
        userEntity.setAvailableCosmeticEntity(null);

        m_userRepository.save(userEntity);
    }

    @Override
    public boolean existsInTable(final String p_uuid) {
        return m_userRepository.existsById(p_uuid);
    }

    @Override
    public UserEntity getUserEntity(final String p_uuid) {
        return m_userRepository.findById(p_uuid).orElseThrow();
    }

    @Override
    public boolean cosmeticExists(final int p_id) {
        return m_availableCosmeticRepository.existsById(p_id);
    }

    @Override
    public boolean ownsCosmetic(final String p_uuid, final AvailableCosmeticEntity p_availableCosmeticEntity) {
        final List<AvailableCosmeticEntity> availableCosmeticEntities = getOwnedCosmetics(p_uuid);

        boolean found = false;

        for (AvailableCosmeticEntity p_cosmetic : availableCosmeticEntities) {
            if (p_cosmetic.getId() == p_availableCosmeticEntity.getId()) {
                found = true;
                break;
            }
        }

        return found;
    }

    @Override
    public List<AvailableCosmeticEntity> getOwnedCosmetics(final String p_uuid) {
        return m_ownedCosmeticRepository.findAllByUserEntity_Uuid(p_uuid).stream()
                .map(OwnedCosmeticEntity::getAvailableCosmeticEntity)
                .toList();
    }

    @Override
    public AvailableCosmeticEntity getCosmetic(final int p_id) {
        return m_availableCosmeticRepository.findById(p_id).orElse(null);
    }

    @Override
    public void updateCosmetic(final String p_uuid, final AvailableCosmeticEntity p_availableCosmeticEntity) {
        final UserEntity userEntity = m_userRepository.findById(p_uuid).orElseThrow();

        userEntity.setAvailableCosmeticEntity(p_availableCosmeticEntity);

        m_userRepository.save(userEntity);
    }

    @Override
    public RankEntity getRank(final String p_uuid) {
        Optional<UserEntity> optionalRank = m_userRepository.findById(p_uuid);

        if (optionalRank.isPresent()) {
            return  optionalRank.get().getRankEntity();
        }
        return m_rankRepository.findById(1).orElseThrow();
    }

    @Override
    public List<ActiveInfo> getActiveInformation() {
        final List<ActiveInfo> activeInfoList = new ArrayList<>();

        final List<UserEntity> allUsers = m_userRepository.findAll();
        final List<UserEntity> onlineUsers = allUsers.stream()
                .filter(UserEntity::getOnline)
                .toList();

        for (UserEntity userEntity : onlineUsers) {
            final ActiveInfo activeInfo = getActiveInformation(userEntity, "fetch");

            activeInfoList.add(activeInfo);
        }

        return activeInfoList;
    }

    @Override
    public ActiveInfo getActiveInformation(final UserEntity p_userEntity, final String p_type) {
        final AvailableCosmeticEntity cosmeticId;
        if (p_userEntity.getAvailableCosmeticEntity() == null) {
            cosmeticId = null;
        } else {
            cosmeticId = p_userEntity.getAvailableCosmeticEntity();
        }

        return new ActiveInfo(
                p_type, p_userEntity.getUuid(), cosmeticId,
                p_userEntity.getRankEntity().getAssetLocation());
    }
}
