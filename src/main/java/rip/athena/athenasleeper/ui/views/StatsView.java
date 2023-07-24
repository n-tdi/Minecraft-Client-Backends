package rip.athena.athenasleeper.ui.views;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;
import rip.athena.athenasleeper.services.OwnedCosmeticService;
import rip.athena.athenasleeper.services.UserService;
import rip.athena.athenasleeper.ui.MainLayout;
import rip.athena.athenasleeper.ui.components.Card;

@Route(value = "/stats", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class StatsView extends FormLayout {
    private final UserService m_userService;
    private final OwnedCosmeticService m_ownedCosmeticService;

    public StatsView(@Autowired UserService p_userService, @Autowired OwnedCosmeticService p_ownedCosmeticService) {
        m_userService = p_userService;
        m_ownedCosmeticService = p_ownedCosmeticService;

        setResponsiveSteps(new ResponsiveStep("0", 5));

        H1 activeUsers = new H1(String.valueOf(m_userService.getAmountOnline()));
        H1 totalUsers = new H1(String.valueOf(m_userService.getTotal()));
        H1 totalCosmeticsInCirculation = new H1(String.valueOf(m_ownedCosmeticService.countAllOwnedCosmetics()));

        add(
                createStatCard("Active Users", activeUsers),
                createStatCard("Total Users", totalUsers),
                createStatCard("Purchased Cosmetics", totalCosmeticsInCirculation)
        );

        getStyle().set("margin-left", "2rem");
    }

    private Card createStatCard(final String p_title, final H1 p_value) {
        final Card usersCard = new Card(p_title);
        p_value.setWidthFull();
        p_value.getStyle().set("text-align", "right");
        usersCard.addItemToContainer(p_value);

        return usersCard;
    }
}
