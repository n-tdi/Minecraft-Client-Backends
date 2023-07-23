package rip.athena.athenasleeper.socket.sockets;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;
import rip.athena.athenasleeper.AthenaSleeperApplication;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.model.UserSession;
import rip.athena.athenasleeper.services.UserService;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
public class UpdateCosmeticSubSocket extends SubSocket {
    private UserService m_userService;

    @Override
    public void handleTextMessage(final WebSocketSession p_session, final Map<String, Object> p_payload) throws IOException {
        final int cosmeticId = (int) ((double) p_payload.get("cosmetic"));

        if (!m_userService.cosmeticExists(cosmeticId)) {
            return; // check if cosmetic is valid cosmetic
        }

        AvailableCosmeticEntity availableCosmeticEntity = m_userService.getCosmetic(cosmeticId);

        Objects.requireNonNull(availableCosmeticEntity);
        // Because of our previous check it is safe to assume the cosmetic
        // id won't be null. unless something has seriously gone wrong

        UserSession userSession = AthenaSleeperApplication.getUserWebSocketSessions().get(p_session.getId());

        // check if owns cosmetic
        if (!m_userService.ownsCosmetic(userSession.getUuid(), availableCosmeticEntity)) {
            return; // don't own no cosmetic broke ass
        }

        // update database and notify all other connectors
        m_userService.updateCosmetic(userSession.getUuid(), availableCosmeticEntity);

        final String updatePayload = new Gson().toJson(m_userService.getActiveInformation(userSession.getUserEntity(), "update"));
        userSession.broadcastToOthers(updatePayload);
    }
}
