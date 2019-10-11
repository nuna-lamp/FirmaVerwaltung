package hello;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "company")
public class Company implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = hello.Customer.class)
	@JoinColumn(name = "company_id")
	private List<Customer> customers;

	protected Company() {
	}

	public Company (String name){
		companyName = name;
	}

	public Company(String companyName, String street, String postCode, String city, String country, String vaxID, String webSite) {
		this.id = id;
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
		if(null == customers){
			return new ArrayList<>();
		}
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void addCustomer(Customer customer){
		this.getCustomers().add(customer);
	}


	@Override
	public String toString() {
		return String.format("Company[id = '%d',companyName='%s', street='%s', postCode='%s', city='%s', country='%s', vaxID='%s', webSite='%s']", id,
				companyName, street, postCode, city, country,vaxID,webSite);
	}



}
