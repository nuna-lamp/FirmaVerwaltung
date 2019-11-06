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

@Route (value = "Companies")
public class CompanyView extends PageView {

    private final CompanyRepository companyRepository;
    private CompanyEditor companyEditor;

    final HorizontalLayout layout;
    final Grid<Company> grid;
    final TextField filter2;
    private final Button addNewCompany;

    public CompanyView(CompanyRepository companyRepository, CompanyEditor companyEditor) {

        this.companyRepository = companyRepository;
        this.companyEditor = companyEditor;
        this.layout = new HorizontalLayout();
        this.grid = new Grid<>(Company.class);
        this.filter2 = new TextField();
        this.addNewCompany = new Button("New company", VaadinIcon.PLUS.create());

        initHeaders();

        HorizontalLayout actions2 = new HorizontalLayout(filter2, addNewCompany);
        add(actions2, grid, companyEditor);

        grid.setHeight("300px");
        grid.setColumns("id", "companyName", "street", "postCode", "city", "country", "vaxID", "webSite");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter2.setPlaceholder("Filter Customer name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter2.setValueChangeMode(ValueChangeMode.EAGER);
        filter2.addValueChangeListener(e -> listCompanies(e.getValue()));

        // Connect selected Customer and Company to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            companyEditor.editCompany(e.getValue());
        });

        // Instantiate and edit new Customer and Company new button is clicked
        addNewCompany.addClickListener(e ->
                companyEditor.editCompany(new Company("", "", "", "", "", "", "")));

        // Listen changes made by the editor, refresh data from backend
        companyEditor.setChangeHandler(() -> {
            companyEditor.setVisible(false);
        });

        // Initialize listing
        listCompanies(null);
        initFooters();
    }

    void listCompanies(String companyText) {
        if (StringUtils.isEmpty(companyText)) {
            grid.setItems(companyRepository.findAll());
        } else {
            List companies = new ArrayList<Customer>();
            companies.addAll(companyRepository.findByCompanyNameStartsWithIgnoreCase(companyText));
            grid.setItems(companies);
        }
    }

}


