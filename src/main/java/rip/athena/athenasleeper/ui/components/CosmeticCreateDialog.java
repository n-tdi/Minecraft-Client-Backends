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

    private TextField m_displayNameField;
    private Select<Boolean> m_animatedSelect;
    private NumberField m_numberField;
    private AssetUpload m_assetUpload;

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

        m_displayNameField = new TextField("Display Name");

        m_animatedSelect = new Select<>();
        m_animatedSelect.setLabel("Animated");
        m_animatedSelect.setItems(true, false);
        m_animatedSelect.setValue(false);

        m_numberField = new NumberField("Frames (Set to -1 if not animated)");
        m_numberField.setMin(-1);
        m_numberField.setValue(-1D);
        m_numberField.setStep(1D);

        m_assetUpload = new AssetUpload(p_fileServingService, new MemoryBuffer());

        VerticalLayout dialogLayout = new VerticalLayout(m_displayNameField, m_animatedSelect, m_numberField, m_assetUpload);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Add", e -> {

            final String displayName = m_displayNameField.getValue();
            if (displayName == null || displayName.trim().isEmpty()) {
                return;
            }

            final boolean animated = m_animatedSelect.getValue();

            final int frames = m_numberField.getValue().intValue();

            if (m_assetUpload.getInputStream() == null) {
                return;
            }



            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }
}
