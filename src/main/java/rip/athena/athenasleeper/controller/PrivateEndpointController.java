package rip.athena.athenasleeper.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.repository.AvailableCosmeticRepository;
import rip.athena.athenasleeper.repository.MasterKeyRepository;
import rip.athena.athenasleeper.services.AvailableCosmeticService;
import rip.athena.athenasleeper.services.OwnedCosmeticService;
import rip.athena.athenasleeper.services.UserService;

@RestController
@RequestMapping("/api/v1/private/")
@AllArgsConstructor
public class PrivateEndpointController {
    private MasterKeyRepository m_masterKeyRepository;
    private AvailableCosmeticService m_availableCosmeticService;
    private OwnedCosmeticService m_ownedCosmeticService;
    private UserService m_userService;

    @PostMapping("/create/cosmetic")
    public ResponseEntity<Void> createCosmetic(@RequestParam String key, @RequestParam String displayName,
                                               @RequestParam boolean animated, @RequestParam(required = false) Integer frames,
                                               @RequestParam String assetLocation) {
        if (!m_masterKeyRepository.existsById(key)) { // Permission check
            return ResponseEntity.status(401).build();
        }

        m_availableCosmeticService.createCosmetic(displayName, animated, frames, assetLocation);
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
}
