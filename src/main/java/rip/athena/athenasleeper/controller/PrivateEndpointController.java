package rip.athena.athenasleeper.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.repository.MasterKeyRepository;
import rip.athena.athenasleeper.services.AvailableCosmeticService;
import rip.athena.athenasleeper.services.OwnedCosmeticService;
import rip.athena.athenasleeper.services.RankService;
import rip.athena.athenasleeper.services.UserService;

/**********************************************************************************
 * ENDPOINT GUIDE
 * /api/v1/private/ - Prefix for all requests
 * <p>
 * /create/cosmetic - Create a cosmetic
 * /add/cosmetic - add a cosmetic to a user's list of available cosmetics
 * /remove/cosmetic - remove a cosmetic from a user's list of available cosmetics
 * /create/rank - create a rank for people to have
 * /set/rank/ - set a user's rank
 * <p>
 * you're welcome ziue :)
 ***********************************************************************************/
@RestController
@RequestMapping("/api/v1/private/")
@AllArgsConstructor
public class PrivateEndpointController {
    private MasterKeyRepository m_masterKeyRepository;
    private AvailableCosmeticService m_availableCosmeticService;
    private OwnedCosmeticService m_ownedCosmeticService;
    private UserService m_userService;
    private RankService m_rankService;

    @PostMapping("/create/cosmetic")
    public ResponseEntity<Void> createCosmetic(@RequestParam String key, @RequestParam String displayName,
                                               @RequestParam String category,
                                               @RequestParam boolean animated, @RequestParam(required = false) Integer frames,
                                               @RequestParam String assetLocation) {
        if (!m_masterKeyRepository.existsById(key)) { // Permission check
            return ResponseEntity.status(401).build();
        }

        m_availableCosmeticService.createCosmetic(displayName, category, animated, frames);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/cosmetic")
    public ResponseEntity<Void> addCosmetic(@RequestParam String key, @RequestParam String uuid, @RequestParam int cosmeticId) {
        if (!m_masterKeyRepository.existsById(key)) { // Permission check
            return ResponseEntity.status(401).build();
        }

        if (!m_userService.existsInTable(uuid) || !m_userService.cosmeticExists(cosmeticId)) {
            return ResponseEntity.badRequest().build();
        }

        final UserEntity userEntity = m_userService.getUserEntity(uuid);
        final AvailableCosmeticEntity availableCosmeticEntity = m_userService.getCosmetic(cosmeticId);

        m_ownedCosmeticService.addCosmetic(userEntity, availableCosmeticEntity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove/cosmetic")
    public ResponseEntity<Void> removeCosmetic(@RequestParam String key, @RequestParam String uuid, @RequestParam int cosmeticId) {
        if (!m_masterKeyRepository.existsById(key)) { // Permission check
            return ResponseEntity.status(401).build();
        }

        if (!m_userService.cosmeticExists(cosmeticId)) {
            return ResponseEntity.ok().build();
        }
        if (!m_userService.existsInTable(uuid)) {
            return ResponseEntity.badRequest().build();
        }

        final UserEntity userEntity = m_userService.getUserEntity(uuid);
        final AvailableCosmeticEntity availableCosmeticEntity = m_userService.getCosmetic(cosmeticId);

        m_ownedCosmeticService.removeCosmetic(userEntity, availableCosmeticEntity);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create/rank")
    public ResponseEntity<Void> createRank(@RequestParam String key, @RequestParam String rankName, @RequestParam String assetLocation) {
        if (!m_masterKeyRepository.existsById(key)) { // Permission check
            return ResponseEntity.status(401).build();
        }

        m_rankService.createRank(rankName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/set/rank")
    public ResponseEntity<Void> setRank(@RequestParam String key, @RequestParam int rankId, @RequestParam String uuid) {
        if (!m_masterKeyRepository.existsById(key)) { // Permission check
            return ResponseEntity.status(401).build();
        }

        if (!m_userService.existsInTable(uuid) || !m_rankService.rankExists(rankId)) {
            return ResponseEntity.badRequest().build();
        }

        final RankEntity rankEntity = m_rankService.getRank(rankId);

        m_userService.setRank(uuid, rankEntity);

        return ResponseEntity.ok().build();
    }
}
