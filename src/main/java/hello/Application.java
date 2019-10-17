package hello;

import com.fasterxml.classmate.AnnotationConfiguration;
import com.vaadin.flow.data.provider.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	private static SessionFactory factory;

	public static void main(String[] args) {

		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner loadData(CustomerRepository repository, CompanyRepository companyRepository) {
		return (args) -> {

			Company company = new Company("LAMP");

			Customer newcustomer = new Customer();
			newcustomer.setFirstName("Nuna");
			newcustomer.setLastName("Bopp");


			//companyRepository.save(company);


			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");



		};
	}

}



