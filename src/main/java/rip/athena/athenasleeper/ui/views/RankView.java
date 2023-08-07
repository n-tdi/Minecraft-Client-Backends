package rip.athena.athenasleeper.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.CosmeticForRankEntity;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.repository.AvailableCosmeticRepository;
import rip.athena.athenasleeper.repository.CosmeticForRankRepository;
import rip.athena.athenasleeper.repository.RankRepository;
import rip.athena.athenasleeper.services.CosmeticForRankService;
import rip.athena.athenasleeper.services.FileServingService;
import rip.athena.athenasleeper.services.RankService;
import rip.athena.athenasleeper.ui.MainLayout;
import rip.athena.athenasleeper.ui.components.RankCreateDialog;
import rip.athena.athenasleeper.utility.HostnameUtil;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "/ranks", layout = MainLayout.class)
public class RankView extends VerticalLayout {
    public RankView(@Autowired final RankService p_rankService,
                    @Autowired final FileServingService p_fileServingService,
                    @Autowired final RankRepository p_rankRepository,
                    @Autowired final AvailableCosmeticRepository p_availableCosmeticRepository,
                    @Autowired final CosmeticForRankRepository p_cosmeticRankRepository,
                    @Autowired final CosmeticForRankService p_cosmeticForRankService) {
        // Get all ranks available
        final ListDataProvider<RankEntity> rankEntityListDataProvider = new ListDataProvider<>(p_rankRepository.findAll());

        // Get all cosmetics available
        final List<AvailableCosmeticEntity> availableCosmeticEntities = p_availableCosmeticRepository.findAll();

        // Get all the stored ranks and their cosmetics already attached to them
        final List<CosmeticForRankEntity> cosmeticForRankEntities = p_cosmeticRankRepository.findAll();

        final Grid<RankEntity> rankEntityGrid = new Grid<>();
        rankEntityGrid.setDataProvider(rankEntityListDataProvider);
        rankEntityGrid.setHeight(80F, Unit.VH);

        rankEntityGrid.addColumn(RankEntity::getRankName).setHeader("Name");
        rankEntityGrid.addComponentColumn(p_rankEntity -> {
            final Button viewButton = new Button("View");
            viewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            viewButton.addClickListener(p_buttonClickEvent -> {
                final String urlToAsset = HostnameUtil.resolveUrl("/api/v1/public/rank/" + p_rankEntity.getRankId() + ".png");
                UI.getCurrent().getPage().open(urlToAsset, "_blank");
            });

            return viewButton;
        }).setHeader("View Asset");
        rankEntityGrid.addComponentColumn(p_rankEntity -> {
            HorizontalLayout horizontalLayout = new HorizontalLayout();

            MultiSelectComboBox<AvailableCosmeticEntity> cosmeticEntityMultiSelectComboBox = new MultiSelectComboBox<>();
            cosmeticEntityMultiSelectComboBox.setItems(availableCosmeticEntities);
            cosmeticEntityMultiSelectComboBox.setItemLabelGenerator(AvailableCosmeticEntity::getDisplayName);
            cosmeticEntityMultiSelectComboBox.select(
                    cosmeticForRankEntities.stream()
                            .filter(p_cosmeticForRankEntity -> p_cosmeticForRankEntity.getRankEntity().getRankId() == p_rankEntity.getRankId())
                            .map(CosmeticForRankEntity::getAvailableCosmeticEntity)
                            .collect(Collectors.toList())
            );

            final Button saveButton = new Button("Save");

            saveButton.addClickListener(p_buttonClickEvent -> {
                p_cosmeticForRankService.setCosmeticForRank(p_rankEntity, cosmeticEntityMultiSelectComboBox.getSelectedItems());

                Notification successNotification = Notification.show("Successfully updated " + p_rankEntity.getRankName() + " cosmetics");
                successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            });

            horizontalLayout.add(cosmeticEntityMultiSelectComboBox, saveButton);
            return horizontalLayout;
        }).setHeader("Cosmetics");

        final RankCreateDialog createDialog = new RankCreateDialog(p_fileServingService, p_rankService, p_rankRepository);

        final Button createCosmeticButton = new Button(new Span(VaadinIcon.TAG.create(), new Span(" Create New Rank "), VaadinIcon.TAG.create()));
        createCosmeticButton.setWidthFull();
        createCosmeticButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        createCosmeticButton.addClickListener(p_buttonClickEvent -> {
            createDialog.open();
        });

        add(rankEntityGrid, createCosmeticButton, createDialog);
    }
}
