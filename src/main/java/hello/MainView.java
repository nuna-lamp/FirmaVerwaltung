package hello;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.hibernate.SessionFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Route
public class MainView extends VerticalLayout {

	private static SessionFactory factory;
	private final CustomerRepository repo;
	private final CompanyRepository companyRepository;

	private final CustomerEditor editor;
	private CompanyEditor companyEditor;

	final Grid<Customer> grid;
	final Grid<Company> companies;


	final TextField filter1;
	final TextField filter2;
	final TextField filter3;

	private final Button addNewBtn;
	private final Button addNewCompany;

	public MainView(CustomerRepository repo, CompanyRepository companyRepository, CustomerEditor editor, CompanyEditor companyEditor) {
		this.repo = repo;
		this.companyRepository = companyRepository;

		this.editor = editor;
		this.companyEditor = companyEditor;

		this.companies = new Grid<>(Company.class);
		this.grid = new Grid<>(Customer.class);


		this.filter1 = new TextField();
		this.filter2 = new TextField();
		this.filter3 = new TextField();


		this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());
		this.addNewCompany = new Button("New company", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions1 = new HorizontalLayout(filter1);
		HorizontalLayout actions2 = new HorizontalLayout(filter2, filter3,addNewBtn, addNewCompany);


		add(actions1, grid, editor);
		add(actions2, companies, companyEditor);

		grid.setHeight("300px");
		grid.setColumns("id", "firstName", "lastName","company");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);


		companies.setHeight("300px");
		companies.setColumns("id", "companyName", "street", "postCode", "city", "country", "vaxID", "webSite");
		companies.getColumnByKey("id").setWidth("50px").setFlexGrow(0);


		filter1.setPlaceholder("Filter first name or Company name");
		filter2.setPlaceholder("Filter company name");
		filter3.setPlaceholder("Filter customer name");


		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter1.setValueChangeMode(ValueChangeMode.EAGER);
		filter1.addValueChangeListener(e -> listCustomers(e.getValue()));

		filter2.setValueChangeMode(ValueChangeMode.EAGER);
		filter2.addValueChangeListener(e -> listCompanies(e.getValue()));

		filter3.setValueChangeMode(ValueChangeMode.EAGER);
		filter3.addValueChangeListener(e -> findCustomerName(e.getValue()));

		// Connect selected Customer and Company to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});
		companies.asSingleSelect().addValueChangeListener(e -> {
			companyEditor.editCompany(e.getValue());
		});

		// Instantiate and edit new Customer and Company new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));
		addNewCompany.addClickListener(e -> companyEditor.editCompany(new Company("", "", "", "", "", "", "")));
		// Listen changes made by the editor, refresh data from backend

		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter1.getValue());
		});

		companyEditor.setChangeHandler(() -> {
			companyEditor.setVisible(false);
			listCompanies(filter2.getValue());
		});

		// Initialize listing
		listCustomers(null);
		listCompanies(null);
	}

	// tag::listCustomers[]
	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());

		} else {

			List customers = new ArrayList<Customer>();

			customers.addAll(companyRepository.findCustomerByComapnyName(filterText));
			customers.addAll(repo.findByFirstNameStartsWithIgnoreCase(filterText));

			grid.setItems(customers);
			//grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}

	void listCompanies(String companyText) {
		if (StringUtils.isEmpty(companyText)) {
			companies.setItems(companyRepository.findAll());
		} else {
			companies.setItems(companyRepository.findByCompanyNameStartsWithIgnoreCase(companyText));
		}

		//companies.setItems(companyRepository.findAll());
	}

	void findCustomerName(String filter) {
		if (StringUtils.isEmpty(filter)) {
			grid.setItems(repo.findAll());

		} else {
			grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filter));
		}
	}
}
// end::listCustomers[]

