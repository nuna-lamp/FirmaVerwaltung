package hello;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.*;
import hello.Customer;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

	List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);
	List<Customer> findByFirstNameStartsWithIgnoreCase(String firstName);

}
