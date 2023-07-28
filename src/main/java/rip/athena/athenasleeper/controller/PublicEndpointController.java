package rip.athena.athenasleeper.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rip.athena.athenasleeper.AthenaSleeperApplication;
import rip.athena.athenasleeper.services.FileServingService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public/")
@AllArgsConstructor
public class PublicEndpointController {
    private FileServingService m_fileServingService;

    @GetMapping("/users")
    private List<String> getOnlineAthenaUsers() {
        List<String> uuids = new ArrayList<>();
        AthenaSleeperApplication.getUserWebSocketSessions().values().forEach(p_userSession -> uuids.add(p_userSession.getUuid()));
        return uuids;
    }

    @RequestMapping("/cosmetic/{id}.png")
    @ResponseBody
    public HttpEntity<byte[]> getCosmeticStatic(@PathVariable int id) throws IOException {
        byte[] image = m_fileServingService.getByteInfoOfStoreCosmeticImage(id);

        if (image.length < 2) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);

        return new HttpEntity<byte[]>(image, headers);
    }

    @RequestMapping("/cosmetic/{id}.gif")
    @ResponseBody
    public HttpEntity<byte[]> getCosmeticAnimated(@PathVariable int id) throws IOException {
        byte[] image = m_fileServingService.getByteInfoOfStoreCosmeticAnimated(id);

        if (image.length < 2) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_GIF);
        headers.setContentLength(image.length);

        return new HttpEntity<byte[]>(image, headers);
    }
}
