package rip.athena.athenasleeper.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.catalina.util.ErrorPageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import rip.athena.athenasleeper.entity.AvailableCosmeticEntity;
import rip.athena.athenasleeper.entity.CosmeticForRankEntity;
import rip.athena.athenasleeper.entity.OwnedCosmeticEntity;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.repository.AvailableCosmeticRepository;
import rip.athena.athenasleeper.repository.OwnedCosmeticRepository;
import rip.athena.athenasleeper.repository.UserRepository;
import rip.athena.athenasleeper.services.OwnedCosmeticService;
import rip.athena.athenasleeper.ui.MainLayout;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@PageTitle("Users | Athena CRM")
@Route(value = "/users", layout = MainLayout.class)
public class UsersView extends VerticalLayout {
    private final UserRepository m_userRepository;
    private final Grid<UserEntity> m_userEntityGrid;
    public UsersView(@Autowired UserRepository p_userRepository, @Autowired AvailableCosmeticRepository p_availableCosmeticRepository,
                     @Autowired OwnedCosmeticRepository p_ownedCosmeticRepository, @Autowired OwnedCosmeticService p_ownedCosmeticService) {
        m_userRepository = p_userRepository;

        m_userEntityGrid = new Grid<>(UserEntity.class, false);
        m_userEntityGrid.appendHeaderRow();
        final GridListDataView<UserEntity> dataView = m_userEntityGrid.setItems(m_userRepository.findAll());
        m_userEntityGrid.setHeight(80F, Unit.VH);

        m_userEntityGrid.addColumn(UserEntity::getUsername).setHeader("Username");
        m_userEntityGrid.addColumn(UserEntity::getOnline).setHeader("Online");
        m_userEntityGrid.addColumn(p_userEntity -> p_userEntity.getRankEntity().getRankName()).setHeader("Rank");
        m_userEntityGrid.addColumn(p_userEntity -> {
            if (p_userEntity.getAvailableCosmeticEntity() != null) {
                return p_userEntity.getAvailableCosmeticEntity().getDisplayName();
            }
            return "None";
        }).setHeader("Equipped Cosmetic");
        m_userEntityGrid.addColumn(UserEntity::getUuid).setHeader("UUID");

        m_userEntityGrid.setItemDetailsRenderer(
                createUserDetailRenderer(
                        p_availableCosmeticRepository, p_availableCosmeticRepository.findAll(),
                        p_ownedCosmeticService, p_ownedCosmeticRepository.findAll()
                )
        );

        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());

        dataView.addFilter(person -> {
            String searchTerm = searchField.getValue().trim();

            if (searchTerm.isEmpty())
                return true;

            return person.getUsername().toLowerCase().contains(searchTerm.toLowerCase());
        });

        add(m_userEntityGrid, searchField);
    }

    private static ComponentRenderer<UserDetails, UserEntity> createUserDetailRenderer(
            final AvailableCosmeticRepository p_availableCosmeticRepository,
            final List<AvailableCosmeticEntity> p_availableCosmeticEntities,
            final OwnedCosmeticService p_ownedCosmeticService,
            final List<OwnedCosmeticEntity> p_ownedCosmeticEntities
    ) {
        return new ComponentRenderer<>(userEntity ->
                new UserDetails(
                        userEntity, p_availableCosmeticRepository, p_availableCosmeticEntities, p_ownedCosmeticService,
                        p_ownedCosmeticEntities
                )
        );
    }

    private static class UserDetails extends VerticalLayout {
        public UserDetails(
                final UserEntity p_userEntity,
                final AvailableCosmeticRepository p_availableCosmeticRepository, final List<AvailableCosmeticEntity> p_availableCosmeticEntities,
                final OwnedCosmeticService p_ownedCosmeticService, final List<OwnedCosmeticEntity> p_ownedCosmeticEntities
                ) {

            final HorizontalLayout buttonLayout = new HorizontalLayout();
            buttonLayout.setWidthFull();

            // Ranks Selector Layout

            // Cosmetics Selector
            final MultiSelectComboBox<AvailableCosmeticEntity> availableCosmeticSelector = new MultiSelectComboBox<>();
            availableCosmeticSelector.setItems(p_availableCosmeticEntities);
            availableCosmeticSelector.setItemLabelGenerator(AvailableCosmeticEntity::getDisplayName);
            availableCosmeticSelector.setWidthFull();
            availableCosmeticSelector.setLabel("Owned Cosmetics");
            availableCosmeticSelector.select(
                    p_ownedCosmeticEntities.stream()
                            .filter(p_ownedCosmeticEntity ->
                                    p_ownedCosmeticEntity.getUserEntity().getUuid().equals(p_userEntity.getUuid()))
                            .map(OwnedCosmeticEntity::getAvailableCosmeticEntity)
                            .collect(Collectors.toList())
            );

            buttonLayout.add(availableCosmeticSelector);

            // Save Button
            final Button saveButton = new Button("Save");
            saveButton.setWidthFull();
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            saveButton.addClickListener(p_buttonClickEvent -> {
                p_ownedCosmeticService.setCosmetic(p_userEntity, availableCosmeticSelector.getSelectedItems());

                UI.getCurrent().getPage().reload();
            });

            add(buttonLayout, saveButton);
            setWidthFull();
        }
    }
}
