package rip.athena.athenasleeper.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Emphasis;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;

@CssImport(value = "./styles/cards.css")
public class Card extends Div {
    private final Div m_container;

    public Card(final String p_title) {
        m_container = new Div();

        m_container.add(new H4(p_title));
        m_container.setClassName("container");
        getStyle().remove("margin-left");

        add(m_container);
        setClassName("card");
        setWidthFull();
    }

    public void addItemToContainer(final Component... components) {
        m_container.add(components);
    }
}
