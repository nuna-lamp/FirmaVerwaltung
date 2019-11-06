package hello;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

public class PageView extends VerticalLayout {


    protected void initHeaders() {
        //Header
        setDefaultHorizontalComponentAlignment(Alignment.START);
        setSizeFull();
        H1 head = new H1("LAMP-Solutions");
        head.getElement().getThemeList().add("dark");
        add(head);

        //Menue
        Tab tab1 = new Tab();
        tab1.add(new RouterLink("Customers", CustomerView.class));

        Tab tab2 = new Tab();
        tab2.add(new RouterLink("Companies", CompanyView.class));

        Tabs tabs = new Tabs(tab1, tab2);
        if (this instanceof CustomerView) {
            tabs.setSelectedTab(tab1);
        } else if (this instanceof CompanyView) {
            tabs.setSelectedTab(tab2);
        } else {
            tabs.setSelectedTab(null);

        }
        add(tabs);
    }
    protected void  initViewbody() {
        H1 view1 = new H1("welcome ... i am a body page");
        H1 view2 = new H1("i am a body page.....");
        add(view1,view2);
    }

    protected void initFooters() {
        Label abc = new Label("Footer: Email...");
        HorizontalLayout footer = new HorizontalLayout(abc);
        footer.setPadding(true);
        footer.setSpacing(true);
        add(footer);
    }
}

