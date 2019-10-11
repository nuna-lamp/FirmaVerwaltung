package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner loadData(CustomerRepository repository, CompanyRepository companyRepository) {
		return (args) -> {

			Company company = new Company("MyCompany");

			Customer newcustomer = new Customer();
			newcustomer.setFirstName("test");
			newcustomer.setCompany(company);

			repository.save(newcustomer);

			company.addCustomer(newcustomer);


			companyRepository.save(company);


			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

		};
	}

	public CommandLineRunner loadData(CompanyRepository repository) {
		return (args) -> {

			log.info("Companies found with findAll():");
			log.info("-------------------------------");
			for (Company company : repository.findAll()) {
				log.info(company.toString());
			}
			log.info("");

		};
	}


}
