package hello;

import java.util.ArrayList;
import java.util.List;

public interface CompanyRepositoryCustom {
    public List<Customer> findCustomerByComapnyName(String name);
}
