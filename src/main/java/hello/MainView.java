package hello;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {


	private final CustomerRepository repo;

	private final CustomerEditor editor;

	final Grid<Customer> grid;

	final TextField filter;

	private final Button addNewBtn;
	private final Button searchBtn;

	public MainView(CustomerRepository repo, CustomerEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Customer.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());
		this.searchBtn = new Button("Search Company Name", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("id","companyName","firstName","lastName","street","postCode","city","country","vaxID","webSite");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filter by company name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "","","","","","","","")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}

	// tag::listCustomers[]
	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		} else {
			grid.setItems(repo.findByCompanyNameStartsWithIgnoreCase(filterText));
		}
	}
}
// end::listCustomers[]


/*
	private final FirmaRepository repo;

	private final FirmaEditor editor;

	final Grid<Firma> grid;

	final TextField filter;

	private final Button addNewBtn;

	public MainView(FirmaRepository repo, FirmaEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(Firma.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New Firma", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, editor);

		grid.setHeight("300px");
		grid.setColumns("fid", "firmaName", "strasse", "");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listFirma(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.edit(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.edit(new Firma("", "","","","","","")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listFirma(filter.getValue());
		});

		// Initialize listing
		listFirma(null);
	}



    // tag::listCustomers[]
	void listFirma(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByFirmaNameStartsWithIgnoreCase(filterText));
		}
	}
	// end::listCustomers[]
}

*/
