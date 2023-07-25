package rip.athena.athenasleeper.ui.views;

import com.vaadin.flow.component.dialog.Dialog;
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
import rip.athena.athenasleeper.ui.components.AssetUpload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

@PageTitle("Cosmetics | Athena CRM")
@Route(value = "/cosmetics", layout = MainLayout.class)
@Slf4j
public class CosmeticsView extends VerticalLayout {

    public CosmeticsView(@Autowired FileServingService p_fileServingService) {


//        MemoryBuffer memoryBuffer = new MemoryBuffer();
//        AssetUpload assetUpload = new AssetUpload(p_fileServingService, memoryBuffer);
//        add(assetUpload);
    }
}
