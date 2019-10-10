package hello;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.then;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.annotation.PostConstruct;

@RunWith(MockitoJUnitRunner.class)
public class CustomerEditorTests {

	private static final String COMPANY_NAME = "LAMP";
	private static final String FIRST_NAME = "Nuna";
	private static final String LAST_NAME = "Bopp";
	private static final String STREET = "Gotenhof1";
	private static final String POST_CODE = "90433";
	private static final String CITY = "Nuernberg";
	private static final String COUNTRY = "Deutschland";
	private static final String VAX_ID = "DE123654";
	private static final String WEBSITE = "www.lamp.de";

	@Mock CustomerRepository customerRepository;
	@InjectMocks CustomerEditor editor;
	@Mock CustomerEditor.ChangeHandler changeHandler;

	@Before
	public void init() {
		editor.setChangeHandler(changeHandler);
	}

	@Test
	public void shouldStoreCustomerInRepoWhenEditorSaveClicked() {
		emptyCustomerWasSetToForm();

		this.editor.companyName.setValue(COMPANY_NAME);
		this.editor.lastName.setValue(LAST_NAME);
		this.editor.firstName.setValue(FIRST_NAME);
		this.editor.street.setValue(STREET);
		this.editor.postCode.setValue(POST_CODE);
		this.editor.city.setValue(CITY);
		this.editor.country.setValue(COUNTRY);
		this.editor.vaxID.setValue(VAX_ID);
		this.editor.webSite.setValue(WEBSITE);

		this.editor.save();

		then(this.customerRepository).should().save(argThat(customerMatchesEditorFields()));
	}

	@Test
	public void shouldDeleteCustomerFromRepoWhenEditorDeleteClicked() {
		customerDataWasFilled();

		editor.delete();

		then(this.customerRepository).should().delete(argThat(customerMatchesEditorFields()));
	}

	private void emptyCustomerWasSetToForm() {
		this.editor.editCustomer(new Customer());
	}
	private void customerDataWasFilled() {
		this.editor.editCustomer(new Customer(COMPANY_NAME,FIRST_NAME, LAST_NAME,STREET,POST_CODE,CITY,COUNTRY,VAX_ID,WEBSITE));
	}

	private ArgumentMatcher<Customer> customerMatchesEditorFields() {
		return customer -> COMPANY_NAME.equals(customer.getCompanyName())
				&& FIRST_NAME.equals(customer.getFirstName())
				&& LAST_NAME.equals(customer.getLastName())
				&& STREET.equals(customer.getStreet())
				&& POST_CODE.equals(customer.getPostCode())
				&& CITY.equals(customer.getCity())
				&& COUNTRY.equals(customer.getCountry())
				&& VAX_ID.equals(customer.getVaxID())
				&& WEBSITE.equals(customer.getWebSite());
	}

}
