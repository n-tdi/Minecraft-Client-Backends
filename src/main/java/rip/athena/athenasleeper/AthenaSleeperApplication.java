package rip.athena.athenasleeper;

import jakarta.websocket.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
@SpringBootApplication
public class AthenaSleeperApplication extends SpringBootServletInitializer {

	private static final Class applicationClass = AthenaSleeperApplication.class;
	public static final Map<UUID, WebSocketSession> userWebSocketSessions = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(AthenaSleeperApplication.class, args);

	}

}