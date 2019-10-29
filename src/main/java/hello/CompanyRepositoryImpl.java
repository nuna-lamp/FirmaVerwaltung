package hello;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.random;

public class CompanyRepositoryImpl implements CompanyRepositoryCustom{

    @Autowired
    CompanyRepository companyRepository;

    @PersistenceContext
    EntityManager entityManager;
/*
    public List<Customer> findCustomerByComapnyName(String filterText){

        int random = (int)Math.random();
        List customers = new ArrayList<Customer>();


        if(random%2==0){
            List companies = companyRepository.findByCompanyNameStartsWithIgnoreCase(filterText);
            Iterator<Company> it = companies.iterator();
            while(it.hasNext()){
                Company current = it.next();
                customers.addAll(current.getCustomers());
            }
        }

        return customers;


    }

 */
    public List<Customer> findCustomerByComapnyName(String filterText) {
        Query query = entityManager.createNativeQuery("" +
                        "SELECT" +
                        " * " +
                        "FROM " +
                        "customer " +
                        "INNER JOIN company ON " +
                        "company.id = company_id " +
                        "WHERE " +
                        "company.company_name like ?"

                , Customer.class);

        query.setParameter(1, "%" + filterText + "%");

        return query.getResultList();
    }
}
