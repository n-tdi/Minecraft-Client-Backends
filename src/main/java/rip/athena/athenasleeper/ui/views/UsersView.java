package rip.athena.athenasleeper.ui.views;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import rip.athena.athenasleeper.entity.UserEntity;
import rip.athena.athenasleeper.repository.UserRepository;
import rip.athena.athenasleeper.ui.MainLayout;

@PageTitle("Users | Athena CRM")
@Route(value = "/users", layout = MainLayout.class)
public class UsersView extends VerticalLayout {
    private final UserRepository m_userRepository;
    private final Grid<UserEntity> m_userEntityGrid;
    public UsersView(@Autowired UserRepository p_userRepository) {
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
}
