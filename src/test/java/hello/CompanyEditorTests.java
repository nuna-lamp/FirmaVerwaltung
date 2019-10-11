package hello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class CompanyEditorTests {


	private static final String firmaName = "LAMP";
	private static final String strasse = "Gotenhof";
	private static final String plz = "90402";
	private static final String ort = "Nürnberg";
	private static final String land = "Deutschland";
	private static final String ustID = "123654";
	private static final String webSite = "www.lamp.de";


	@Mock
	CompanyRepository companyRepository;
	@InjectMocks
	CompanyEditor editor;
	@Mock CompanyEditor.ChangeHandler changeHandler;

	@Before
	public void init() {
		editor.setChangeHandler(changeHandler);
	}

	@Test
	public void shouldStoreFirmaInRepoWhenEditorSaveClicked() {
		emptyFirmaWasSetToForm();

		this.editor.companyName.setValue(firmaName);
		this.editor.street.setValue(strasse);
		this.editor.postCode.setValue(plz);
		this.editor.city.setValue(ort);
		this.editor.country.setValue(land);
		this.editor.vaxID.setValue(ustID);
		this.editor.webSite.setValue(webSite);

		this.editor.save();

		then(this.companyRepository).should().save(argThat(firmaMatchesEditorFields()));
	}

	@Test
	public void shouldDeleteFirmaFromRepoWhenEditorDeleteClicked() {
		firmaDataWasFilled();

		editor.delete();

		then(this.companyRepository).should().delete(argThat(firmaMatchesEditorFields()));
	}

	private void emptyFirmaWasSetToForm() {
		this.editor.edit(new Company());
	}
	private void firmaDataWasFilled() {
		this.editor.edit(new Company(firmaName, strasse, plz, ort, land, ustID, webSite));
	}

	private ArgumentMatcher<Company> firmaMatchesEditorFields() {
		return company -> firmaName.equals(company.getCompanyName())
				&& strasse.equals(company.getStreet())
				&& plz.equals(company.getPostCode())
				&& ort.equals(company.getCity())
				&& land.equals(company.getCountry())
				&& ustID.equals(company.getVaxID())
				&& webSite.equals(company.getWebSite());
	}

}
