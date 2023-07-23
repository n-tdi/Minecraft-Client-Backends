package rip.athena.athenasleeper;

import jakarta.annotation.PostConstruct;
import jakarta.websocket.Session;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.socket.WebSocketSession;
import rip.athena.athenasleeper.entity.MasterKeyEntity;
import rip.athena.athenasleeper.model.UserSession;
import rip.athena.athenasleeper.repository.MasterKeyRepository;
import rip.athena.athenasleeper.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
@SpringBootApplication
public class AthenaSleeperApplication extends SpringBootServletInitializer {

	private static final Class applicationClass = AthenaSleeperApplication.class;
	@Getter
	private static final Map<String, UserSession> m_userWebSocketSessions = new HashMap<>();
	@Autowired
	private UserRepository m_userRepository0;
	private static UserRepository m_userRepository;

	@PostConstruct
	private void postConstructor() {
		m_userRepository = this.m_userRepository0;
	}

	public static void main(String[] args) {
		SpringApplication.run(AthenaSleeperApplication.class, args);
	}

	public static void put(final String p_uuid, final WebSocketSession p_webSocketSession) {
		final UserSession userSession = new UserSession(p_uuid, p_webSocketSession, m_userRepository);
		m_userWebSocketSessions.put(p_webSocketSession.getId(), userSession);
	}

	public static void remove(final String p_id) {
		m_userWebSocketSessions.remove(p_id);
	}
}