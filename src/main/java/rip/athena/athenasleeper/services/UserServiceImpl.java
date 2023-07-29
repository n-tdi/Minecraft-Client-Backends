package rip.athena.athenasleeper.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rip.athena.athenasleeper.entity.*;
import rip.athena.athenasleeper.external.PlayerDB;
import rip.athena.athenasleeper.model.ActiveInfo;
import rip.athena.athenasleeper.model.UserSession;
import rip.athena.athenasleeper.repository.*;
import rip.athena.athenasleeper.utility.HostnameUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository m_userRepository;
    private RankRepository m_rankRepository;
    private OwnedCosmeticRepository m_ownedCosmeticRepository;
    private AvailableCosmeticRepository m_availableCosmeticRepository;
    private CosmeticForRankRepository m_cosmeticForRankRepository;
    private PlayerDB m_playerDB;

    @Override
    public void userLogOn(final UserSession p_userSession) {
        if (!existsInTable(p_userSession.getUuid())) {
            insertIntoTable(p_userSession.getUuid());
        }

        final UserEntity userEntity = m_userRepository.findById(p_userSession.getUuid()).orElseThrow();
        userEntity.setOnline(true);

        final String username = m_playerDB.getUsername(UUID.fromString(p_userSession.getUuid()));
        userEntity.setUsername(username);

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
    public long getAmountOnline() {
        return m_userRepository.countAllByOnline(true);
    }

    @Override
    public long getTotal() {
        return m_userRepository.count();
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
        final UserEntity userEntity = m_userRepository.findById(p_uuid).orElseThrow();
        final RankEntity rankEntity = userEntity.getRankEntity();

        final List<AvailableCosmeticEntity> ownedCosmetics = m_ownedCosmeticRepository.findAllByUserEntity_Uuid(p_uuid).stream()
                .map(OwnedCosmeticEntity::getAvailableCosmeticEntity)
                .collect(Collectors.toList());

        final List<AvailableCosmeticEntity> rankOwnedCosmetics = m_cosmeticForRankRepository.findAllByRankEntity(rankEntity)
                .stream()
                .map(CosmeticForRankEntity::getAvailableCosmeticEntity)
                .toList();

        ownedCosmetics.addAll(rankOwnedCosmetics);

        return ownedCosmetics;
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
    public void setRank(final String p_uuid, final RankEntity p_rankEntity) {
        final UserEntity userEntity = m_userRepository.findById(p_uuid).orElseThrow();
        userEntity.setRankEntity(p_rankEntity);
        m_userRepository.save(userEntity);
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
        final AvailableCosmeticEntity cosmeticEntity;
        if (p_userEntity.getAvailableCosmeticEntity() == null) {
            cosmeticEntity = null;
        } else {
            cosmeticEntity = p_userEntity.getAvailableCosmeticEntity();
        }

        final RankEntity rankEntity = p_userEntity.getRankEntity();

        return new ActiveInfo(
                p_type, p_userEntity.getUuid(), cosmeticEntity,
                HostnameUtil.resolveUrl("/api/v1/public/rank/" + rankEntity.getRankId() + ".png")
        );
    }
}
