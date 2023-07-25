package rip.athena.athenasleeper.ui.views;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import rip.athena.athenasleeper.services.FileServingService;
import rip.athena.athenasleeper.ui.MainLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

@PageTitle("Cosmetics | Athena CRM")
@Route(value = "/cosmetics", layout = MainLayout.class)
@Slf4j
public class CosmeticsView extends VerticalLayout {

    public CosmeticsView(@Autowired FileServingService p_fileServingService) {
        MemoryBuffer memoryBuffer = new MemoryBuffer();
        Upload singleFileUpload = new Upload(memoryBuffer);
        singleFileUpload.setAcceptedFileTypes("image/png", ".png");

        final Random r = new Random();

        singleFileUpload.addSucceededListener(event -> {
            InputStream fileData = memoryBuffer.getInputStream();
            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();

            log.info("Mime Type: {}, File Name: {}, Content Length: {}", mimeType, fileName, contentLength);
            try {
                p_fileServingService.storeCosmeticFile(fileData, r.nextInt(69420));
            } catch (IOException p_e) {
                throw new RuntimeException(p_e);
            }
        });

        singleFileUpload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();

            Notification notification = Notification.show(errorMessage, 5000,
                    Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        add(singleFileUpload);
    }
}
