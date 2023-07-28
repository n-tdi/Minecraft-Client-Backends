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
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.repository.RankRepository;
import rip.athena.athenasleeper.services.FileServingService;
import rip.athena.athenasleeper.services.RankService;

@Slf4j
public class RankCreateDialog extends Dialog {

    private TextField m_displayNameField;
    private Select<Boolean> m_animatedSelect;
    private NumberField m_numberField;
    private AssetUpload m_assetUpload;
    private final RankService m_rankService;
    private final RankRepository m_rankRepository;

    public RankCreateDialog(@Autowired FileServingService p_fileServingService,
                            @Autowired RankService p_rankService,
                            @Autowired RankRepository p_rankRepository) {
        super();
        m_rankService = p_rankService;
        m_rankRepository = p_rankRepository;

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

        m_assetUpload = new AssetUpload(p_fileServingService, new MemoryBuffer());

        VerticalLayout dialogLayout = new VerticalLayout(m_displayNameField, m_assetUpload);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "36em").set("max-width", "100%");

        return dialogLayout;
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Add", e -> {
            final String displayName = m_displayNameField.getValue();
            if (displayName == null || displayName.trim().isEmpty()) {
                return;
            }

            if (m_assetUpload.getInputStream() == null) {
                return;
            }

            final RankEntity rankEntity = m_rankService.createRank(displayName);

            m_assetUpload.storeFile(rankEntity.getRankId(), false, false);

            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }
}
