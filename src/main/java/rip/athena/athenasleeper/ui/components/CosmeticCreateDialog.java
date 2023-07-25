package rip.athena.athenasleeper.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rip.athena.athenasleeper.services.FileServingService;

@Slf4j
@Component
public class CosmeticCreateDialog extends Dialog {
    public CosmeticCreateDialog(@Autowired FileServingService p_fileServingService) {
        super();
        setHeaderTitle("New Cosmetic");

        VerticalLayout dialogLayout = createDialogLayout(p_fileServingService);
        add(dialogLayout);

        Button saveButton = createSaveButton(this);
        Button cancelButton = new Button("Cancel", e -> close());
        getFooter().add(cancelButton);
        getFooter().add(saveButton);
    }

    private VerticalLayout createDialogLayout(final FileServingService p_fileServingService) {

        final TextField displayNameField = new TextField("Display Name");

        final Select<Boolean> animatedSelect = new Select<>();
        animatedSelect.setLabel("Animated");
        animatedSelect.setItems(true, false);
        animatedSelect.setValue(false);

        final NumberField numberField = new NumberField("Frames (Set to -1 if not animated)");
        numberField.setMin(-1);
        numberField.setValue(-1D);

        final AssetUpload assetUpload = new AssetUpload(p_fileServingService, new MemoryBuffer());

        VerticalLayout dialogLayout = new VerticalLayout(displayNameField, animatedSelect);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Add", e -> dialog.close());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }
}
