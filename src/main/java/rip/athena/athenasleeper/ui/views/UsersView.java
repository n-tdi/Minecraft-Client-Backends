package rip.athena.athenasleeper.ui.views;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import rip.athena.athenasleeper.ui.MainLayout;

@Route(value = "/users", layout = MainLayout.class)
public class UsersView extends VerticalLayout {
    public UsersView() {
        add(new Span("Hello World!"));
    }
}
