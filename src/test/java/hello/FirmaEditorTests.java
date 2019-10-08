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
public class FirmaEditorTests {


	private static final String firmaName = "LAMP";
	private static final String strasse = "Gotenhof";
	private static final String plz = "90402";
	private static final String ort = "Nürnberg";
	private static final String land = "Deutschland";
	private static final String ustID = "123654";
	private static final String webSite = "www.lamp.de";


	@Mock FirmaRepository firmaRepository;
	@InjectMocks FirmaEditor editor;
	@Mock FirmaEditor.ChangeHandler changeHandler;

	@Before
	public void init() {
		editor.setChangeHandler(changeHandler);
	}

	@Test
	public void shouldStoreFirmaInRepoWhenEditorSaveClicked() {
		emptyFirmaWasSetToForm();

		this.editor.firmaName.setValue(firmaName);
		this.editor.strasse.setValue(strasse);
		this.editor.plz.setValue(plz);
		this.editor.ort.setValue(ort);
		this.editor.land.setValue(land);
		this.editor.ustID.setValue(ustID);
		this.editor.webSite.setValue(webSite);

		this.editor.save();

		then(this.firmaRepository).should().save(argThat(firmaMatchesEditorFields()));
	}

	@Test
	public void shouldDeleteFirmaFromRepoWhenEditorDeleteClicked() {
		firmaDataWasFilled();

		editor.delete();

		then(this.firmaRepository).should().delete(argThat(firmaMatchesEditorFields()));
	}

	private void emptyFirmaWasSetToForm() {
		this.editor.edit(new Firma());
	}
	private void firmaDataWasFilled() {
		this.editor.edit(new Firma(firmaName, strasse, plz, ort, land, ustID, webSite));
	}

	private ArgumentMatcher<Firma> firmaMatchesEditorFields() {
		return firma -> firmaName.equals(firma.getFirmaName())
				&& strasse.equals(firma.getStrasse())
				&& plz.equals(firma.getPlz())
				&& ort.equals(firma.getOrt())
				&& land.equals(firma.getLand())
				&& ustID.equals(firma.getUstId())
				&& webSite.equals(firma.getWebSite());
	}

}
