package rip.athena.athenasleeper.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import rip.athena.athenasleeper.entity.RankEntity;
import rip.athena.athenasleeper.repository.RankRepository;
import rip.athena.athenasleeper.services.FileServingService;
import rip.athena.athenasleeper.services.RankService;
import rip.athena.athenasleeper.ui.MainLayout;
import rip.athena.athenasleeper.utility.HostnameUtil;

@Route(value = "/ranks", layout = MainLayout.class)
public class RankView extends VerticalLayout {
    public RankView(@Autowired final RankService p_rankService,
                         @Autowired final FileServingService p_fileServingService,
                         @Autowired final RankRepository p_rankRepository) {
        final ListDataProvider<RankEntity> rankEntityListDataProvider = new ListDataProvider<>(p_rankRepository.findAll());

        final Grid<RankEntity> rankEntityGrid = new Grid<>();
        rankEntityGrid.setDataProvider(rankEntityListDataProvider);

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

//        final CosmeticCreateDialog cosmeticCreateDialog = new CosmeticCreateDialog(p_fileServingService, p_availableCosmeticService, p_availableCosmeticRepository);

//        final Button createCosmeticButton = new Button(new Span(VaadinIcon.HEART.create(), new Span(" Create New Cosmetic "), VaadinIcon.HEART.create()));
//        createCosmeticButton.setWidthFull();
//        createCosmeticButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
//        createCosmeticButton.addClickListener(p_buttonClickEvent -> {
//            cosmeticCreateDialog.open();
//        });

        add(rankEntityGrid/*,createCosmeticButton, cosmeticCreateDialog */);
    }
}
