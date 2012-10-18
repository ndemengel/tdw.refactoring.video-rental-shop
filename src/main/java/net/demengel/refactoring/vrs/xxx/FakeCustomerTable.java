package net.demengel.refactoring.vrs.xxx;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static net.demengel.refactoring.vrs.xxx.FakeDbUtils.parseDate;

import java.util.Date;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Customer;

import com.google.common.base.Predicate;

/**
 * Fake CUSTOMER table in a relational DataBase.
 */
public class FakeCustomerTable {

    private static final List<Customer> ALL_CUSTOMERS = asList(
            customer("11111").name("John Doe").birthDate("1980/07/13").credits(65).build(),
            customer("22222").name("Arthur Dent").birthDate("1976/02/20").credits(12).build()
            );

    public static List<Customer> selectAllPropertiesFromCustomerTable() {
        return newArrayList(ALL_CUSTOMERS);
    }

    public static List<Customer> selectAllPropertiesFromCustomerTableWhereAccountNumberStartsWith(final String accountNumberStart) {
        return newArrayList(filter(ALL_CUSTOMERS, new Predicate<Customer>() {
            public boolean apply(Customer customer) {
                return customer.getAccountNumber().startsWith(accountNumberStart);
            }
        }));
    }

    public static List<Customer> selectAllPropertiesFromCustomerTableWhereNameContains(final String nameContents) {
        return newArrayList(filter(ALL_CUSTOMERS, new Predicate<Customer>() {
            public boolean apply(Customer customer) {
                return customer.getName().contains(nameContents);
            }
        }));
    }

    private static CustomerBuilder customer(String accountNumber) {
        return new CustomerBuilder(accountNumber);
    }

    private static class CustomerBuilder {

        private Customer customer;

        public CustomerBuilder(String accountNumber) {
            customer = new Customer();
            customer.setAccountNumber(accountNumber);
        }

        public Customer build() {
            return customer;
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
