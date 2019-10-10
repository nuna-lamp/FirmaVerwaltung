package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;

@Entity
@Table (name = "customer")
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	private String companyName;
	private String firstName;
	private String lastName;
	private String street;
	private String postCode;
	private String city;
	private String country;
	private String vaxID;
	private String webSite;


	protected Customer() {
	}

	public Customer(String companyName, String firstName, String lastName, String street, String postCode, String city, String country, String vaxID, String webSite)
	{
		this.companyName = companyName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.postCode = postCode;
		this.city = city;
		this.country = country;
		this.vaxID = vaxID;
		this.webSite = webSite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getVaxID() {
		return vaxID;
	}

	public void setVaxID(String vaxID) {
		this.vaxID = vaxID;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	@Override
	public String toString() {
		return String.format("Customer[id=%d, companyName = '%s',firstName='%s', " +
						"lastName='%s', street ='%s', postCode='%s', city='%s', country='%s', vaxID='%s', webSite='%s']", id,companyName, firstName,
				firstName, lastName, street,postCode, city, country, vaxID, webSite);
	}

}
