package rip.athena.athenasleeper.socket.handlers;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.UUID;

@Component
public class IdentificationHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
        final Map<String, String> payload = new Gson().fromJson(message.getPayload(), Map.class);

        if (payload.getOrDefault("type", "none").equals("join")) {
            final UUID uuid = UUID.fromString(payload.get("uuid"));
            // Create and insert into database if needed here
        }
    }
}
