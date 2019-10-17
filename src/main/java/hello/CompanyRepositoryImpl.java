package hello;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.Math.random;

public class CompanyRepositoryImpl implements CompanyRepositoryCustom{

    @Autowired
    CompanyRepository companyRepository;

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

}
