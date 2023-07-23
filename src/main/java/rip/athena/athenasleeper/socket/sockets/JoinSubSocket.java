package rip.athena.athenasleeper.socket.sockets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import rip.athena.athenasleeper.AthenaSleeperApplication;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class JoinSubSocket extends SubSocket{
    @Override
    public void handleTextMessage(final WebSocketSession p_session, final Map<String, String> p_payload) throws IOException {
        final UUID uuid = UUID.fromString(p_payload.get("uuid"));
        AthenaSleeperApplication.put(uuid, p_session);
        p_session.sendMessage(new TextMessage("Welcome " + uuid));
        log.info("Created User Session from {}", p_session.getRemoteAddress().getAddress().getHostAddress());
    }
}
