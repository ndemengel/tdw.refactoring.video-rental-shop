package net.demengel.refactoring.vrs.xxx;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static net.demengel.refactoring.vrs.xxx.FakeDbUtils.doInTransaction;
import static net.demengel.refactoring.vrs.xxx.FakeDbUtils.parseDate;

import java.util.Date;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.dao.Transaction;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * Fake CUSTOMER table in a relational database. Static methods of this class represents SQL queries made to the database.
 */
public class FakeCustomerTable {

    private static final List<Customer> ALL_CUSTOMERS = newArrayList();
    static {
        newCustomer("11111").name("John Doe").birthDate("1980/07/13").creditCardNumber("1111-2222-3333-4444").credits(65).addressLine1("221B Baker Street")
                .zipCode("W1U 6RS").city("LONDON").phoneNumber("+33 6 67 67 89 89").insert();
        newCustomer("22222").name("Arthur Dent").birthDate("1976/02/20").creditCardNumber("5555-6666-7777-8888").addressLine1("Sector ZZ9")
                .addressLine2("Plural Z Alpha").zipCode("ZZ9").city("Betelgeuse Seven").credits(12).phoneNumber("+89 1011 1213").insert();
    }

    public static List<Customer> selectAllPropertiesFromCustomerTable() {
        return copy(ALL_CUSTOMERS);
    }

    private static List<Customer> copy(Iterable<Customer> customers) {
        return newArrayList(transform(customers, new Function<Customer, Customer>() {
            public Customer apply(Customer in) {
                Customer out = new Customer();
                out.setAccountNumber(in.getAccountNumber());
                out.setAddressLine1(in.getAddressLine1());
                out.setAddressLine2(in.getAddressLine2());
                out.setAddressLine3(in.getAddressLine3());
                out.setBirthDate(in.getBirthDate());
                out.setCity(in.getCity());
                out.setCreditCardNumber(in.getCreditCardNumber());
                out.setCredits(in.getCredits());
                out.setName(in.getName());
                out.setPhoneNumber(in.getPhoneNumber());
                out.setZipCode(in.getZipCode());
                return out;
            }
        }));
    }

    public static List<Customer> selectAllPropertiesFromCustomerTableWhereAccountNumberStartsWith(final String accountNumberStart) {
        return copy(filter(ALL_CUSTOMERS, new Predicate<Customer>() {
            public boolean apply(Customer customer) {
                return customer.getAccountNumber().startsWith(accountNumberStart);
            }
        }));
    }

    public static List<Customer> selectAllPropertiesFromCustomerTableWhereNameContains(final String nameContents) {
        return copy(filter(ALL_CUSTOMERS, new Predicate<Customer>() {
            public boolean apply(Customer customer) {
                return customer.getName().contains(nameContents);
            }
        }));
    }

    public static void updateCustomerTableSetAllPropertiesWhereCustomerIsEqualTo(final Customer customer, Transaction transaction) {
        doInTransaction(transaction, new Runnable() {
            @Override
            public void run() {
                Customer customerToUpdate = find(ALL_CUSTOMERS, new Predicate<Customer>() {
                    public boolean apply(Customer it) {
                        return it.getAccountNumber().equals(customer.getAccountNumber());
                    }
                });
                customerToUpdate.setAddressLine1(customer.getAddressLine1());
                customerToUpdate.setAddressLine2(customer.getAddressLine2());
                customerToUpdate.setAddressLine3(customer.getAddressLine3());
                customerToUpdate.setCity(customer.getCity());
                customerToUpdate.setCreditCardNumber(customer.getCreditCardNumber());
                customerToUpdate.setCredits(customer.getCredits());
                customerToUpdate.setPhoneNumber(customer.getPhoneNumber());
                customerToUpdate.setZipCode(customer.getZipCode());
            }
        });
    }

    public static void truncate() {
        ALL_CUSTOMERS.clear();
    }

    public static CustomerBuilder newCustomer(String accountNumber) {
        return new CustomerBuilder(accountNumber);
    }

    public static class CustomerBuilder {

        private Customer customer;

        private CustomerBuilder(String accountNumber) {
            customer = new Customer();
            customer.setAccountNumber(accountNumber);
        }

        public void insert() {
            ALL_CUSTOMERS.add(customer);
        }

        public CustomerBuilder name(String name) {
            customer.setName(name);
            return this;
        }

        public CustomerBuilder birthDate(Date birthDate) {
            customer.setBirthDate(birthDate);
            return this;
        }

        public CustomerBuilder birthDate(String birthDate) {
            customer.setBirthDate(parseDate(birthDate));
            return this;
        }

        public CustomerBuilder addressLine1(String addressLine1) {
            customer.setAddressLine1(addressLine1);
            return this;
        }

        public CustomerBuilder addressLine2(String addressLine2) {
            customer.setAddressLine2(addressLine2);
            return this;
        }

        public CustomerBuilder addressLine3(String addressLine3) {
            customer.setAddressLine3(addressLine3);
            return this;
        }

        public CustomerBuilder city(String city) {
            customer.setCity(city);
            return this;
        }

        public CustomerBuilder zipCode(String zipCode) {
            customer.setZipCode(zipCode);
            return this;
        }

        public CustomerBuilder phoneNumber(String phoneNumber) {
            customer.setPhoneNumber(phoneNumber);
            return this;
        }

        public CustomerBuilder creditCardNumber(String creditCardNumber) {
            customer.setCreditCardNumber(creditCardNumber);
            return this;
        }

        public CustomerBuilder credits(int credits) {
            customer.setCredits(credits);
            return this;
        }
    }
}
