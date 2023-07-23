package rip.athena.athenasleeper.socket.sockets;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import rip.athena.athenasleeper.AthenaSleeperApplication;
import rip.athena.athenasleeper.model.ActiveInfo;
import rip.athena.athenasleeper.model.UserSession;
import rip.athena.athenasleeper.services.UserService;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
public class JoinSubSocket extends SubSocket{
    private UserService m_userService;

    @Override
    public void handleTextMessage(final WebSocketSession p_session, final Map<String, Object> p_payload) throws IOException {
        final UUID uuid = UUID.fromString((String) p_payload.get("uuid"));

        if (AthenaSleeperApplication.getUserWebSocketSessions().containsKey(p_session.getId())) {
            return;
        }



        AthenaSleeperApplication.put(uuid.toString(), p_session);

        m_userService.userLogOn(AthenaSleeperApplication.getUserWebSocketSessions().get(p_session.getId()));

        String responsePayload = new Gson().toJson(m_userService.getActiveInformation());

        p_session.sendMessage(new TextMessage(responsePayload));

        final UserSession currentUserSession = AthenaSleeperApplication.getUserWebSocketSessions().get(p_session.getId());

        final ActiveInfo activeInfo = m_userService.getActiveInformation(currentUserSession.getUserEntity(), "update");
        String updatePayload = new Gson().toJson(activeInfo);

        currentUserSession.broadcastToOthers(updatePayload);

        log.info("Created User Session from {}", p_session.getRemoteAddress().getAddress().getHostAddress());
    }
}
