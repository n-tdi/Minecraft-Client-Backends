package rip.athena.athenasleeper.external;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.UUID;

@Component
public class PlayerDB {
    private final WebClient m_webClient;
    private final Gson m_gson;

    public PlayerDB() {
        m_webClient = WebClient.builder()
                .baseUrl("https://playerdb.co/api/player/minecraft/")
                .defaultHeader("user-agent", "Java SpringBoot WebSocket - Contact me@ntdi.world")
                .build();

        m_gson = new Gson();
    }

    @SuppressWarnings("unchecked")
    public String getUsername(UUID p_uuid) {
        final String response = m_webClient.get()
                .uri(p_uuid.toString())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        Map<String, Object> jsonMap = m_gson.fromJson(response, Map.class);
        final Map<String, Object> dataMap = (Map<String, Object>) jsonMap.get("data");
        final Map<String, Object> playerMap = (Map<String, Object>) dataMap.get("player");

        return (String) playerMap.get("username"); // All this for decoding json with parents. >:(
    }
}
