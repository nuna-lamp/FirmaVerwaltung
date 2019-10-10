package hello.Firma;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Firma {

	@Id
	@GeneratedValue
	private long fid;
	private String firmaName;
	private String strasse;
	private String plz;
	private String ort;
	private String land;
	private String ustId;
	private String webSite;


	protected Firma() {
	}


	public Firma(String firmaName, String strasse, String plz, String ort, String land, String ustId, String webSite) {
		this.fid = fid;
		this.firmaName = firmaName;
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
		this.land = land;
		this.ustId = ustId;
		this.webSite = webSite;
	}


	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public String getFirmaName() {
		return firmaName;
	}

	public void setFirmaName(String firmaName) {
		this.firmaName = firmaName;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getUstId() {
		return ustId;
	}

	public void setUstId(String ustId) {
		this.ustId = ustId;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	@Override
	public String toString() {
		return String.format("Firma[fid = '%d',firmaName='%s', Strasse='%s', plz='%s', ort='%s', land='%s', ustID='%s', webSite='%s']", fid,
				firmaName, strasse, plz, ort, land,ustId,webSite);
	}



}
