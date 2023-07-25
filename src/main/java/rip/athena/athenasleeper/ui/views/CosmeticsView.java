package rip.athena.athenasleeper.ui.views;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import rip.athena.athenasleeper.ui.MainLayout;

import java.io.InputStream;

@PageTitle("Cosmetics | Athena CRM")
@Route(value = "/cosmetics", layout = MainLayout.class)
@Slf4j
public class CosmeticsView extends VerticalLayout {

    public CosmeticsView() {
        MemoryBuffer memoryBuffer = new MemoryBuffer();
        Upload singleFileUpload = new Upload(memoryBuffer);
        singleFileUpload.setAcceptedFileTypes("image/png", ".png");

        singleFileUpload.addSucceededListener(event -> {
            InputStream fileData = memoryBuffer.getInputStream();
            String fileName = event.getFileName();
            long contentLength = event.getContentLength();
            String mimeType = event.getMIMEType();

            log.info("Mime Type: {}, File Name: {}, Content Length: {}", mimeType, fileName, contentLength);
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
