package hello;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FirmaRepository extends JpaRepository<Firma, String> {

	List<Firma> suchenFirmaName(String firmaName);

}
