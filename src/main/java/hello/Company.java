package hello;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "company")
/*
@NamedQuery(name = "customer.findByLastNameStartsWithIgnoreCase",
		query = "select last_name from customer last_name where last_name.company_name = ?1")

 */
public class Company implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Column(name = "Company_Name")
	private String companyName;
	@Column (name = "Street")
	private String street;
	@Column (name = "Postcode")
	private String postCode;
	@Column (name = "City")
	private String city;
	@Column (name = "Country")
	private String country;
	@Column (name = "VaxID")
	private String vaxID;
	@Column (name = "Website")
	private String webSite;

	@OneToMany(
			cascade = CascadeType.ALL,
			orphanRemoval = true
			,fetch = FetchType.EAGER
	)
	@JoinColumn(name = "company_id")
	private List<Customer> customers = new ArrayList<>();

	protected Company() {
	}

	public Company (String name){
		companyName = name;
	}

	public Company(String companyName, String street, String postCode, String city, String country, String vaxID, String webSite) {
		this.companyName = companyName;
		this.street = street;
		this.postCode = postCode;
		this.city = city;
		this.country = country;
		this.vaxID = vaxID;
		this.webSite = webSite;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void addCustomer(Customer customer)
	{
		this.getCustomers().add(customer);
	}

	@Override
	public String toString() {
		return String.format("Company[id = '%d',companyName='%s', street='%s', postCode='%s', city='%s', country='%s', vaxID='%s', webSite='%s']", id,
				companyName, street, postCode, city, country,vaxID,webSite);
	}

}
