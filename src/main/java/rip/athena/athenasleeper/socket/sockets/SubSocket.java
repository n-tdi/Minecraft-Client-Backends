package rip.athena.athenasleeper.socket.sockets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

public abstract class SubSocket {
    public abstract void handleTextMessage(final WebSocketSession p_session, final Map<String, Object> p_payload) throws IOException;
}
