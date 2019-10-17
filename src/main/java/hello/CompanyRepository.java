package hello;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {

	List<Company> findByCompanyNameStartsWithIgnoreCase(String companyName);

}
