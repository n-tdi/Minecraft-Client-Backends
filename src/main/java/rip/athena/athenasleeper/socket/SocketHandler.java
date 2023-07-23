package rip.athena.athenasleeper.socket;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import rip.athena.athenasleeper.AthenaSleeperApplication;
import rip.athena.athenasleeper.socket.sockets.JoinSubSocket;
import rip.athena.athenasleeper.socket.sockets.SubSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class SocketHandler extends TextWebSocketHandler {
    private final Map<String, SubSocket> m_subSockets = new HashMap<>();

    public SocketHandler() {
        m_subSockets.put("join", new JoinSubSocket());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection received from {}", session.getRemoteAddress().getAddress().getHostAddress());
    }

    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
        final Map<String, String> payload = new Gson().fromJson(message.getPayload(), Map.class);
        final String type = payload.getOrDefault("type", "none");

        for (Map.Entry<String, SubSocket> subSocket : m_subSockets.entrySet()) {
            if (type.equals(subSocket.getKey())) {
                subSocket.getValue().handleTextMessage(session, payload);
            }
        }
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
        log.info("Connection lost from {}", session.getRemoteAddress().getAddress().getHostAddress());
        AthenaSleeperApplication.remove(session.getId());
    }
}
