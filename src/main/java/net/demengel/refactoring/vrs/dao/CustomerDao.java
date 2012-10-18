package net.demengel.refactoring.vrs.dao;

import static net.demengel.refactoring.vrs.xxx.FakeCustomerTable.selectAllPropertiesFromCustomerTable;
import static net.demengel.refactoring.vrs.xxx.FakeCustomerTable.selectAllPropertiesFromCustomerTableWhereAccountNumberStartsWith;
import static net.demengel.refactoring.vrs.xxx.FakeCustomerTable.selectAllPropertiesFromCustomerTableWhereNameContains;

import java.util.List;

import net.demengel.refactoring.vrs.bean.Customer;

/**
 * Responsible for retrieving customer-related data
 * 
 * 
 * @author nico 17 oct. 2012
 */
public class CustomerDao {

    /** The instance */
    private static CustomerDao sInstance;

    /**
     * Default constructor. As this class is a singleton, it is hidden. Use {@link #getInstance()} to get a reference to this DAO.
     */
    private CustomerDao() {
    }

    /**
     * Returns the instance of CustomerDao
     * 
     * @return
     */
    public static synchronized CustomerDao getInstance() {
        if (sInstance == null) {
            sInstance = new CustomerDao();
        }
        return sInstance;

    }

    public List<Customer> listAllCustomers() {
        return selectAllPropertiesFromCustomerTable();
    }

    public List<Customer> findCustomersByAccountNumberStart(String accountNumber) {
        return selectAllPropertiesFromCustomerTableWhereAccountNumberStartsWith(accountNumber);
    }

    public List<Customer> findCustomersByNameLike(String name) {
        return selectAllPropertiesFromCustomerTableWhereNameContains(name);
    }
}
