package hello.Firma;

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
public class FirmaEditor extends VerticalLayout implements KeyNotifier {

    private final FirmaRepository repository;
    private Firma firma;

    /* Fields to edit properties in Firma entity */
    TextField firmaName = new TextField("FirmaName");
    TextField strasse = new TextField("Strasse");
    TextField ort = new TextField("Ort");
    TextField plz = new TextField("Plz");
    TextField land = new TextField("Land");
    TextField ustID = new TextField("UmsatzsteurID");
    TextField webSite = new TextField("WebSite");

    /* Action buttons */
    // TODO why more code?
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Firma> binder = new Binder<>(Firma.class);
    private ChangeHandler changeHandler;


    @Autowired
    public FirmaEditor(FirmaRepository repository) {
        this.repository = repository;

        add(firmaName,strasse,ort,plz,land,ustID,webSite, actions);

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
        cancel.addClickListener(e -> edit(firma));
        setVisible(false);
    }


    void delete() {
        repository.delete(firma);
        changeHandler.onChange();
    }

    void save() {
        repository.save(firma);
        changeHandler.onChange();
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void edit(Firma f) {
        if (f == null) {
            setVisible(false);
            return;
        }
        //final boolean persisted = f.getFirmaName()!= null;
        final boolean persisted = f.getFid()!= 0;
        if (persisted) {
            // Find fresh entity for editing
            //customer = repository.findById(c.getId()).get();

            firma = (Firma) repository.findByFirmaNameStartsWithIgnoreCase(f.getFirmaName().toString());
        }
        else {
            firma = f;
        }
        cancel.setVisible(persisted);

        // Bind Firma properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(firma);

        setVisible(true);

        // Focus FirmaName initially
        firmaName.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }


}
