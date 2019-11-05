package hello;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

public class PageView extends VerticalLayout {


    private CustomerEditor editor;
    private CompanyEditor companyEditor;


    protected void initHeadersAndFooters() {

        setDefaultHorizontalComponentAlignment(Alignment.START);
        setSizeFull();
        H1 head = new H1("LAMP-Solutions");
        head.getElement().getThemeList().add("dark");
        add(head);

        Tab tab1 = new Tab();
        tab1.add(new RouterLink("Customers", CustomerView.class));

        Tab tab2 = new Tab();
        tab2.add(new RouterLink("Companies", CompanyView.class));


        Tabs tabs = new Tabs(tab1, tab2);
        if(this instanceof CustomerView){
            tabs.setSelectedTab(tab1);
        }
        else if(this instanceof CompanyView){
            tabs.setSelectedTab(tab2);

        }
        else{
            tabs.setSelectedTab(null);

        }
        add(tabs);
    }
}
