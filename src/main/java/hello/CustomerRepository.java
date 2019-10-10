package hello;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.*;
import hello.Customer;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByCompanyNameStartsWithIgnoreCase(String CompanyName);



}
