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
public class MainView extends VerticalLayout {
    private static SessionFactory factory;
    private final CustomerRepository repo;
    private final CompanyRepository companyRepository;
    private final CustomerEditor editor;
    private CompanyEditor companyEditor;

    final HorizontalLayout layout;
    final Grid<Customer> grid;
    private Button button = new Button("home");
    //final SplitLayout innenLayout;
    //final MenuBar menuBar;
    //final MenuItem project;
    //final Text selected;
    /*
    private TextArea textArea = new TextArea();

    */

    public MainView(CustomerRepository repo, CompanyRepository companyRepository, CustomerEditor editor, CompanyEditor companyEditor) {
        this.repo = repo;
        this.companyRepository = companyRepository;
        this.editor = editor;
        this.companyEditor = companyEditor;

        this.layout = new HorizontalLayout();
        //this.textArea = new TextArea();
        //this.innenLayout = new SplitLayout();
        //this.menuBar = new MenuBar();
        //this.project = new MenuItem();
        //this.selected = new Text("");
        //this.textLabel = new Label();

        this.grid = new Grid<>(Customer.class);

        setDefaultHorizontalComponentAlignment(Alignment.START);
        setSizeFull();
        H1 head = new H1("LAMP-Solutions");
        head.getElement().getThemeList().add("dark");
        add(head);

        // build layout
        layout.getStyle().set("border", "0px solid #9E9E9E");
        layout.setWidth("100%");
        layout.setHeight("30px");
        layout.setPadding(false);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setJustifyContentMode(JustifyContentMode.START);
        layout.setDefaultVerticalComponentAlignment(Alignment.END);
/*
        Image image = new Image("..webApp/frontend/images/lamp-logo-transparent.png", "Logo");
        image.setWidth("50px");
         setHorizontalComponentAlignment(Alignment.END, button);
        add(image);
*/
        grid.setHeightByRows(true);
        grid.addColumn(Customer::getId);
        grid.addColumn(Customer::getFirstName);
        grid.addColumn(Customer::getLastName);
        grid.addColumn(Customer::getCompany);

/*
        Stream.of("Customers", "Compannies", "Dashboard", "Help").forEach(menuBar::addItem);
        Div menu = new Div();
        menu.add(new RouterLink("Customers", CustomerView.class));
        menu.add(new RouterLink("Companies", CompanyView.class));

        Label title = new Label("Menue");
        Button custom =  new Button("Customers", event -> getNavagator().navigteTo("Costomers"));
        Button compa =  new Button("Companies", event -> getNavagator().navigteTo("Companies"));

        CssLayout viewContainer = new CssLayout(title, custom, compa);

         HorizontalLayout mainLayout = new HorizontalLayout(menuBar, (Component) viewContainer);
         mainLayout.setSizeFull();
         setContent(mainLayout);

         Navigator navigatou = new Navigator(this.grid);
         setContent(mainLayout);
*/
        MenuBar menuBar = new MenuBar();
        Div menu = new Div();

        HorizontalLayout header = new HorizontalLayout();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setFlexGrow(1, (HasElement) menuBar);
        header.setPadding(true);
        header.setSpacing(true);
        add(header);
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

     private void initHeadersAndFooters() {
        Tab tab1 = new Tab();
        tab1.add(new RouterLink("Customers", CustomerView.class));
        tab1.addDetachListener(e -> editor.editCustomer(new Customer("", "")));

        Tab tab2 = new Tab();
        tab2.add(new RouterLink("Companies", CompanyView.class));
        tab2.addDetachListener(e -> companyEditor.editCompany(new Company("", "", "", "", "", "", "")));

        Tabs tabs = new Tabs(tab1, tab2);
        add(tabs);
    }
}


