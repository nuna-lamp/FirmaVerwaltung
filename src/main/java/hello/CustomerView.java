package hello;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Route(value = "Customers")
public class CustomerView extends PageView {
    private final CustomerRepository repo;
    private final CustomerEditor editor;

    final HorizontalLayout layout;
    final Grid<Customer> grid;
    private final Button addNewBtn;
    final TextField filter1;

    public CustomerView(CustomerRepository repo, CustomerEditor editor) {
        this.repo = repo;
        this.editor = editor;

        this.layout = new HorizontalLayout();
        this.grid = new Grid<>(Customer.class);
        this.filter1 = new TextField();
        this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());

        initHeaders();

        HorizontalLayout actions1 = new HorizontalLayout(filter1, addNewBtn);
        add(actions1, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "firstName", "lastName", "company");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter1.setPlaceholder("Filter Company name");

        // Hook logic to components
        // Replace listing with filtered content when user changes filter
        filter1.setValueChangeMode(ValueChangeMode.EAGER);
        filter1.addValueChangeListener(e -> listCustomers(e.getValue()));

        // Connect selected Customer and Company to editor or hide if none is selected

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editCustomer(e.getValue());
        });

        // Instantiate and edit new Customer and Company new button is clicked
        addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));
        // Listen changes made by the editor, refresh data from backend

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
        });

        // Initialize listing
        listCustomers(null);
        initFooters();
    }

    // tag::listCustomers[]
    void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());

        } else {
            List customers = new ArrayList<Customer>();
            customers.addAll(repo.findByFirstNameStartsWithIgnoreCase(filterText));
            grid.setItems(customers);
            //grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
}

