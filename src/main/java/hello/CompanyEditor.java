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

@SpringComponent
@UIScope
public class CompanyEditor extends VerticalLayout implements KeyNotifier {

    private final CompanyRepository repository;
    private Company company;

    /* Fields to edit properties in Firma entity */
    TextField companyName = new TextField("Company name");
    TextField street = new TextField("Street");
    TextField postCode = new TextField("Post code");
    TextField city = new TextField("City");
    TextField country = new TextField("Country");
    TextField vaxID = new TextField("Vax ID");
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
    public CompanyEditor(CompanyRepository repository) {
        this.repository = repository;

        add(companyName,street,postCode,city,country,vaxID,webSite);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> edit(company));
        setVisible(false);
    }


    void delete() {
        repository.delete(company);
        changeHandler.onChange();
    }

    void save() {
        repository.save(company);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void edit(Company f) {
        if (f == null) {
            setVisible(false);
            return;
        }
        //final boolean persisted = f.getFirmaName()!= null;
        final boolean persisted = f.getId()!= 0;
        if (persisted) {
            // Find fresh entity for editing
            //customer = repository.findById(c.getId()).get();

            company = (Company) repository.findByCompanyNameStartsWithIgnoreCase(f.getCompanyName().toString());
        }
        else {
            company = f;
        }
        cancel.setVisible(persisted);

        // Bind Firma properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(company);

        setVisible(true);

        // Focus companyName initially
        companyName.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }


}
