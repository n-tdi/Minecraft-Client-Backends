package rip.athena.athenasleeper.ui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import rip.athena.athenasleeper.services.FileServingService;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class AssetUpload extends Upload {
    private final FileServingService m_fileServingService;
    @Getter
    private InputStream m_inputStream;
    public AssetUpload(FileServingService p_fileServingService, MemoryBuffer p_memoryBuffer) {
        super(p_memoryBuffer);
        m_fileServingService = p_fileServingService;

        this.setAcceptedFileTypes("image/png", ".png", "animated/gif", ".gif");
        this.setMaxFileSize(12 * 1024 * 1024); // 12MB

        this.addSucceededListener(event -> {
            m_inputStream = p_memoryBuffer.getInputStream();

            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();

            log.info("Mime Type: {}, File Name: {}, Content Length: {}", mimeType, fileName, contentLength);
        });

        this.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();

            Notification notification = Notification.show(errorMessage, 5000,
                    Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
    }

    public void storeFile(final int p_id, final boolean cosmetic, final boolean p_animated) {
        if (m_inputStream == null) {
            return;
        }
        try {
            if (cosmetic) {
                if (p_animated) {
                    m_fileServingService.storeCosmeticAnimatedFile(m_inputStream, p_id);
                } else {
                    m_fileServingService.storeCosmeticFile(m_inputStream, p_id);
                }
            } else {
                m_fileServingService.storeRankIconFile(m_inputStream, p_id);
            }
            UI.getCurrent().getPage().reload();
        } catch (IOException p_e) {
            throw new RuntimeException(p_e);
        }
    }
}
