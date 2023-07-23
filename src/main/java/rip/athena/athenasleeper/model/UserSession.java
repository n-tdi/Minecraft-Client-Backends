package rip.athena.athenasleeper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import rip.athena.athenasleeper.AthenaSleeperApplication;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.repository.UserRepository;

import java.io.IOException;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserSession {
    private final String m_uuid;
    private final WebSocketSession m_session;
    private UserRepository m_userRepository;

    public UserEntity getUserEntity() {
        return m_userRepository.findById(m_uuid).orElseThrow();
    }

    public void broadcastToOthers(final String p_payload) throws IOException {
        for (UserSession userSession : AthenaSleeperApplication.getUserWebSocketSessions().values()) {
            if (!userSession.getSession().getId().equals(m_session.getId())) {
                userSession.getSession().sendMessage(new TextMessage(p_payload));
            }
        }
    }
}
