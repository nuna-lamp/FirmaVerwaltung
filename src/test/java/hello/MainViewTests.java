package hello;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainViewTests.Config.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MainViewTests {

	@Autowired
	CustomerRepository repository;

	VaadinRequest vaadinRequest = Mockito.mock(VaadinRequest.class);

	CustomerEditor editor;

	MainView mainView;

	@Before
	public void setup() {
		this.editor = new CustomerEditor(this.repository);
	//	this.mainView = new MainView(this.repository, companyRepository, editor, companies);
	}

	@Test
	public void shouldInitializeTheGridWithCustomerRepositoryData() {
		int customerCount = (int) this.repository.count();

		then(mainView.grid.getColumns()).hasSize(3);
		then(getCustomersInGrid()).hasSize(customerCount);
	}

	private List<Customer> getCustomersInGrid() {
		ListDataProvider<Customer> ldp = (ListDataProvider) mainView.grid.getDataProvider();
		return new ArrayList<>(ldp.getItems());
	}

	@Test
	public void shouldFillOutTheGridWithNewData() {
		int initialCustomerCount = (int) this.repository.count();

		customerDataWasFilled(editor, "Marcin", "Grzejszczak");

		this.editor.save();

		then(getCustomersInGrid()).hasSize(initialCustomerCount + 1);

		then(getCustomersInGrid().get(getCustomersInGrid().size() - 1))
				.extracting("firstName", "lastName")
				.containsExactly("Marcin", "Grzejszczak");

	}

	@Test
	public void shouldFilterOutTheGridWithTheProvidedLastName() {

		this.repository.save(new Customer("Josh", "Long"));

		mainView.listCustomers("Long");

		then(getCustomersInGrid()).hasSize(1);
		then(getCustomersInGrid().get(getCustomersInGrid().size() - 1))
				.extracting("firstName", "lastName")
				.containsExactly("Josh", "Long");
	}

	@Test
	public void shouldInitializeWithInvisibleEditor() {

		then(this.editor.isVisible()).isFalse();
	}

	@Test
	public void shouldMakeEditorVisible() {
		Customer first = getCustomersInGrid().get(0);
		this.mainView.grid.select(first);

		then(this.editor.isVisible()).isTrue();
	}

	private void customerDataWasFilled(CustomerEditor editor, String firstName,
									   String lastName) {
		this.editor.firstName.setValue(firstName);
		this.editor.lastName.setValue(lastName);
		editor.editCustomer(new Customer(firstName, lastName));
	}

	@Configuration
	@EnableAutoConfiguration(exclude = com.vaadin.flow.spring.SpringBootAutoConfiguration.class)
	static class Config {

		@Autowired
		CustomerRepository repository;

		@PostConstruct
		public void initializeData() {
			this.repository.save(new Customer("Jack", "Bauer"));
			this.repository.save(new Customer("Chloe", "O'Brian"));
			this.repository.save(new Customer("Kim", "Bauer"));
			this.repository.save(new Customer("David", "Palmer"));
			this.repository.save(new Customer("Michelle", "Dessler"));
		}
	}
}

 /*
@Autowired
FirmaRepository repository;

	VaadinRequest vaadinRequest = Mockito.mock(VaadinRequest.class);

	FirmaEditor editor;

	MainView mainView;

	public MainViewTests(FirmaRepository repo, FirmaEditor editor) {
		super(repo, editor);
	}

	@Before
	public void setup() {
		this.editor = new FirmaEditor(this.repository);
		this.mainView = new MainView(this.repository, editor);
	}

	@Test
	public void shouldInitializeTheGridWithCustomerRepositoryData() {
		int FirmaCount = (int) this.repository.count();

		then(mainView.grid.getColumns()).hasSize(3);
		then(getFirmenInGrid()).hasSize(FirmaCount);
	}

	private List<Firma> getFirmenInGrid() {
		ListDataProvider<Firma> ldp = (ListDataProvider) mainView.grid.getDataProvider();
		return new ArrayList<>(ldp.getItems());
	}

	@Test
	public void shouldFillOutTheGridWithNewData() {
		int initialFirmaCount = (int) this.repository.count();

		firmaDataWasFilled(editor, "LAMP", "Gotenhof1","90433", "Nuernberg", "Deutschland", "123654","www.lamp.de");

		this.editor.save();

		then(getFirmenInGrid()).hasSize(initialFirmaCount + 1);

		then(getFirmenInGrid().get(getFirmenInGrid().size() - 1))
				.extracting("firmaName", "strasse","plz","ort", "land","ustID", "webSite")
				.containsExactly("LAMP", "Gotenhof1","90433", "Nuernberg", "Deutschland", "123654","www.lamp.de");

	}

	@Test
	public void shouldFilterOutTheGridWithTheProvidedLastName() {

		this.repository.save(new Firma("Nuna", "Breite2","90402", "Nuernberg", "Deutschland", "456987","www.nuna.de"));

		mainView.listFirma("Nuna");

		then(getFirmenInGrid()).hasSize(1);
		then(getFirmenInGrid().get(getFirmenInGrid().size() - 1))
				.extracting("firmaName", "strasse","plz","ort", "land","ustID", "webSite")
				.containsExactly("Nuna", "Breite2","90402", "Nuernberg", "Deutschland", "456987","www.nuna.de");
	}

	@Test
	public void shouldInitializeWithInvisibleEditor() {

		then(this.editor.isVisible()).isFalse();
	}

	@Test
	public void shouldMakeEditorVisible() {
		Firma first = getFirmenInGrid().get(0);
		this.mainView.grid.select(first);

		then(this.editor.isVisible()).isTrue();
	}

	private void firmaDataWasFilled(FirmaEditor editor, String firmaName,
									String strasse, String plz, String ort , String land, String ustID , String webSite) {
		this.editor.firmaName.setValue(firmaName);
		this.editor.strasse.setValue(strasse);
		this.editor.plz.setValue(plz);
		this.editor.ort.setValue(ort);
		this.editor.land.setValue(land);
		this.editor.ustID.setValue(ustID);
		this.editor.webSite.setValue(webSite);
		editor.edit(new Firma(firmaName,strasse,plz,ort,land,ustID,webSite));
	}

	@Configuration
	@EnableAutoConfiguration(exclude = com.vaadin.flow.spring.SpringBootAutoConfiguration.class)
	static class Config {

		@Autowired
		FirmaRepository repository;

		@PostConstruct
		public void initializeData() {
			this.repository.save(new Firma("IT", "Nuernbergstrasse 5", "90422", "Nuernberg", "Deutschland", "145896", "www.it.de"));
			this.repository.save(new Firma("LAMP", "Gotenhof1","90433", "Nuernberg", "Deutschland", "123654","www.lamp.de"));
			this.repository.save(new Firma("CMP", "Erlanger 12", "90555", "Nuernberg", "Deutschland", "122698", "www.cmp.de"));
			this.repository.save(new Firma("CDT", "Peutgasse 4", "90352", "Nuernberg", "Deutschland", "751398", "www.cdt.de"));
			this.repository.save(new Firma("Messe nuernberg", "Allerburger 3", "90417", "Nuernberg", "Deutschland", "853698", "www.messe.de"));
		}
	}
}
 */
