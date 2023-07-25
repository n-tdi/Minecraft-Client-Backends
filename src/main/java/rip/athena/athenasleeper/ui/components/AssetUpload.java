package rip.athena.athenasleeper.ui.components;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rip.athena.athenasleeper.services.FileServingService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

@Slf4j
public class AssetUpload extends Upload {
    public AssetUpload(FileServingService p_fileServingService, MemoryBuffer p_memoryBuffer, final int p_id) {
        super(p_memoryBuffer);
        this.setAcceptedFileTypes("image/png", ".png");

        this.addSucceededListener(event -> {
            InputStream fileData = p_memoryBuffer.getInputStream();
            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();

            log.info("Mime Type: {}, File Name: {}, Content Length: {}", mimeType, fileName, contentLength);
            try {
                p_fileServingService.storeCosmeticFile(fileData, p_id);
            } catch (IOException p_e) {
                throw new RuntimeException(p_e);
            }
        });

        this.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();

            Notification notification = Notification.show(errorMessage, 5000,
                    Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
    }
}
