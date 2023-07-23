package rip.athena.athenasleeper.socket.sockets;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import rip.athena.athenasleeper.AthenaSleeperApplication;
import rip.athena.athenasleeper.services.UserService;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
public class JoinSubSocket extends SubSocket{
    private UserService m_userService;

    @Override
    public void handleTextMessage(final WebSocketSession p_session, final Map<String, String> p_payload) throws IOException {
        final UUID uuid = UUID.fromString(p_payload.get("uuid"));
        AthenaSleeperApplication.put(uuid, p_session);

        String responsePayload = new Gson().toJson(m_userService.getActiveInformation());

        p_session.sendMessage(new TextMessage(responsePayload));

        log.info("Created User Session from {}", p_session.getRemoteAddress().getAddress().getHostAddress());
    }
}
