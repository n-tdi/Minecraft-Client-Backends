package rip.athena.athenasleeper.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rip.athena.athenasleeper.AthenaSleeperApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/public/")
public class PublicEndpointController {
    @GetMapping("/users")
    private List<String> getOnlineAthenaUsers() {
        List<String> uuids = new ArrayList<>();
        AthenaSleeperApplication.getUserWebSocketSessions().values().forEach(p_userSession -> uuids.add(p_userSession.getUuid()));
        return uuids;
    }
}
