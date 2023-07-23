package rip.athena.athenasleeper.ui.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import rip.athena.athenasleeper.ui.MainLayout;
import rip.athena.athenasleeper.ui.components.Card;

@Route(value = "/stats", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class StatsView extends FormLayout {
    public StatsView() {
        setResponsiveSteps(new ResponsiveStep("0", 3));

        final Card usersCard = new Card("Active Users");
        usersCard.addItemToContainer(new H1("0"));

        add(usersCard);
    }
}