package hello.Firma;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FirmaRepository extends JpaRepository<Firma, Long> {

	//List<Firma> suchenFirmaName(String firmaName);

	List<Firma> findByFirmaNameStartsWithIgnoreCase(String lastName);
}
