package hello;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.hibernate.SessionFactory;


@Route
public class MainView extends PageView {
    private static SessionFactory factory;
    private final CustomerRepository repo;
    private final CompanyRepository companyRepository;
    private final CustomerEditor editor;
    private CompanyEditor companyEditor;

    final HorizontalLayout layout;
    final Grid<Customer> grid;
    private Button button = new Button("home");

    public MainView(CustomerRepository repo, CompanyRepository companyRepository, CustomerEditor editor, CompanyEditor companyEditor) {
        this.repo = repo;
        this.companyRepository = companyRepository;
        this.editor = editor;
        this.companyEditor = companyEditor;

        this.layout = new HorizontalLayout();


        this.grid = new Grid<>(Customer.class);


        // build layout
        layout.getStyle().set("border", "0px solid #9E9E9E");
        layout.setWidth("100%");
        layout.setHeight("30px");
        layout.setPadding(false);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setJustifyContentMode(JustifyContentMode.START);
        layout.setDefaultVerticalComponentAlignment(Alignment.END);


        grid.setHeightByRows(true);
        grid.addColumn(Customer::getId);
        grid.addColumn(Customer::getFirstName);
        grid.addColumn(Customer::getLastName);
        grid.addColumn(Customer::getCompany);


        initHeadersAndFooters();

        HorizontalLayout actions0 = new HorizontalLayout(layout);
        add(new H1("Hello welcome "));

        Paragraph text = new Paragraph("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

        VerticalLayout content = new VerticalLayout(text);
        content.setPadding(true);
        add(content);

        TextField name = new TextField("Name");
        Paragraph greeting = new Paragraph("");

        Button button = new Button("Greet", event -> {
            greeting.setText("Hello " + name.getValue());
        });
        add(name, button, greeting);

       // setViewContent(content);
        Label abc = new Label("Footer: Email...");
        HorizontalLayout footer = new HorizontalLayout(abc);
        footer.setPadding(true);
        footer.setSpacing(true);
        add(footer);

    }


}


