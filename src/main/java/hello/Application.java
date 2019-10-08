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
	/*
	public CommandLineRunner loadData(CustomerRepository repository) {
		return (args) -> {

			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

		};
	}

	*/

	public CommandLineRunner loadData(FirmaRepository repository) {
		return (args) -> {

			log.info("Firma found with findAll():");
			log.info("-------------------------------");
			for (Firma firma : repository.findAll()) {
				log.info(firma.toString());
			}
			log.info("");

		};
	}
}
