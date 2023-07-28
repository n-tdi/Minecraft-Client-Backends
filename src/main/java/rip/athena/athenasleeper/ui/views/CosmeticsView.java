package rip.athena.athenasleeper.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.repository.AvailableCosmeticRepository;
import rip.athena.athenasleeper.services.AvailableCosmeticService;
import rip.athena.athenasleeper.services.FileServingService;
import rip.athena.athenasleeper.ui.MainLayout;
import rip.athena.athenasleeper.ui.components.CosmeticCreateDialog;
import rip.athena.athenasleeper.utility.HostnameUtil;

import java.util.Objects;

@PageTitle("Cosmetics | Athena CRM")
@Route(value = "/cosmetics", layout = MainLayout.class)
@Slf4j
public class CosmeticsView extends VerticalLayout {

    public CosmeticsView(@Autowired final AvailableCosmeticRepository p_availableCosmeticRepository,
                         @Autowired final FileServingService p_fileServingService,
                         @Autowired final AvailableCosmeticService p_availableCosmeticService) {
        final ListDataProvider<AvailableCosmeticEntity> cosmeticEntityListDataProvider = new ListDataProvider<>(p_availableCosmeticRepository.findAll());

        final Grid<AvailableCosmeticEntity> cosmeticEntityGrid = new Grid<>();
        cosmeticEntityGrid.setDataProvider(cosmeticEntityListDataProvider);

        cosmeticEntityGrid.addColumn(AvailableCosmeticEntity::getDisplayName).setHeader("Name");
        cosmeticEntityGrid.addColumn(AvailableCosmeticEntity::isAnimated).setHeader("Animated");
        cosmeticEntityGrid.addColumn(p_availableCosmeticEntity -> Objects.requireNonNullElse(p_availableCosmeticEntity.getFrames(), "N/A")).setHeader("Frames");
        cosmeticEntityGrid.addComponentColumn(p_availableCosmeticEntity -> {
            final Button viewButton = new Button("View");
            viewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            viewButton.addClickListener(p_buttonClickEvent -> {
                final String extension = (p_availableCosmeticEntity.isAnimated() ? ".gif" : ".png");
                final String urlToAsset = HostnameUtil.resolveUrl("/api/v1/public/cosmetic/" + p_availableCosmeticEntity.getId() + extension);
                UI.getCurrent().getPage().open(urlToAsset, "_blank");
            });

            return viewButton;
        }).setHeader("View Asset");

        final CosmeticCreateDialog cosmeticCreateDialog = new CosmeticCreateDialog(p_fileServingService, p_availableCosmeticService, p_availableCosmeticRepository);

        final Button createCosmeticButton = new Button(new Span(VaadinIcon.HEART.create(), new Span(" Create New Cosmetic "), VaadinIcon.HEART.create()));
        createCosmeticButton.setWidthFull();
        createCosmeticButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        createCosmeticButton.addClickListener(p_buttonClickEvent -> {
            cosmeticCreateDialog.open();
        });

        add(cosmeticEntityGrid, createCosmeticButton, cosmeticCreateDialog);
    }
}
