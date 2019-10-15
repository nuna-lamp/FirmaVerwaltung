package hello;


import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.gatanaso.MultiselectComboBox;

import java.util.*;
import java.util.concurrent.Callable;

@SpringComponent
@UIScope
public class CompanyEditor extends VerticalLayout implements KeyNotifier {

    private final CompanyRepository companyRepository;
    private Company company;

    /* Fields to edit properties in Company entity */
    TextField companyName = new TextField("CompanyName");
    TextField street = new TextField("Street");
    TextField postCode = new TextField("Postcode");
    TextField city = new TextField("City");
    TextField country = new TextField("Country");
    TextField vaxID = new TextField("VaxID");
    TextField webSite = new TextField("Website");

    /* Action buttons */
    // TODO why more code?
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Company> binder = new Binder<>(Company.class);
    private ChangeHandler changeHandler;


    @Autowired
    public CompanyEditor(CompanyRepository companyRepository, CustomerRepository customerRepository) {
        this.companyRepository = companyRepository;

        // Initialize a list with items

      /*  List<Customer> list = new ArrayList<Customer>();
        Customer customer = new Customer();
        list.add(new Customer("Manu", "bopp"));
        list.add(customer);
        list.add(new Customer("Tobias", "Tobias"));
        list.add(new Customer("Heiko", "Heiko"));*/

        // Initialize the ComboBoxMultiselect
        MultiselectComboBox<Customer> comboBoxMultiselect = new MultiselectComboBox();
        //MultiselectComboBox<String> comboBoxMultiselect = new MultiselectComboBox();
        comboBoxMultiselect.setWidth("200px");
        comboBoxMultiselect.setPlaceholder("you can choose hier");
        comboBoxMultiselect.setLabel("Customers");
        comboBoxMultiselect.setItemLabelGenerator(Customer::getFirstName);


        comboBoxMultiselect.setItems(customerRepository.findAll());
        //comboBoxMultiselect.setItems("Id", "First Name", "Last Name");
        //comboBoxMultiselect.setItems(list);
        comboBoxMultiselect.setRequired(true); // mark as mandatory
        comboBoxMultiselect.setErrorMessage("This field is required"); // set error message
       // comboBoxMultiselect.setCompactMode(true); // enable compact mode
/*
        Set value = new HashSet();
        value.add("Item 2");
        value.add("Item 3");
        comboBoxMultiselect.setValue(value);

        comboBoxMultiselect.setEnabled(false); // mark as disabled
*/
   //      Set<Customer> value = comboBoxMultiselect.getValue();


        add(companyName, street, postCode, city, country, vaxID, webSite, comboBoxMultiselect, actions);


        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

      //comboBoxMultiselect.addValueChangeListener(e -> editCompany(company));
       /*
        comboBoxMultiselect.addSelectionListener(e -> {
            Customer cust = new e.getValue();
            String item = String.format("%d: %s: %s", id.getId(), firsName.getFirstName(), lastName.getLastName);

            cust.setValue(item);
        });
        */
        comboBoxMultiselect.addSelectionListener(e -> {
            e.getAddedSelection(); // get added items
            e.getRemovedSelection();// get removed items
        });

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCompany(company));


        setVisible(false);
    }


    void delete() {
        companyRepository.delete(company);
        changeHandler.onChange();
    }

    void save() {
        companyRepository.save(company);
        changeHandler.onChange();
    }

    public final void editCompany(Company c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != 0;
        if (persisted) {
            // Find fresh entity for editing
            company = companyRepository.findById(c.getId()).get();
        } else {
            company = c;
        }
        cancel.setVisible(persisted);

        // Bind customer properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(company);

        setVisible(true);

        // Focus first name initially
        companyName.focus();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }
}
