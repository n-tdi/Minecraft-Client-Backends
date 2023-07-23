package rip.athena.athenasleeper.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.repository.AvailableCosmeticRepository;
import rip.athena.athenasleeper.repository.MasterKeyRepository;
import rip.athena.athenasleeper.services.AvailableCosmeticService;

@RestController
@RequestMapping("/api/v1/private/")
@AllArgsConstructor
public class PrivateEndpointController {
    private MasterKeyRepository m_masterKeyRepository;
    private AvailableCosmeticService m_availableCosmeticService;

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
}
