package net.demengel.refactoring.vrs.it;

import static com.google.common.collect.Lists.transform;
import static net.demengel.refactoring.vrs.xxx.FakeCustomerTable.selectAllPropertiesFromCustomerTable;
import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.newRental;
import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.it.driver.CustomersScreen;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;

public class CustomerScreenSpec extends VideoRentalStoreIntegrationTestCase {

    CustomersScreen customersScreen;

    @Before
    public void showAvailableMoviesScreen() throws Exception {
        customersScreen = application().showCustomersScreen();
    }

    @Test
    public void should_display_all_customers_when_not_filtered() throws Exception {
        customersScreen.displaysCustomers(namesOfCustomersInDatabase());
    }

    @Test
    public void should_only_display_customers_whose_number_starts_with_user_filter_string() throws Exception {
        // when
        customersScreen.userEntersNumberFilterText("22");

        // then
        customersScreen.displaysCustomers("Arthur Dent", "Zaphod Beeblebox");
    }

    @Test
    public void should_only_display_customer_whose_number_starts_with_user_filter_string() throws Exception {
        // when
        customersScreen.userEntersNumberFilterText("11");

        // then
        customersScreen.displaysCustomers("John Doe");
    }

    @Test
    public void should_not_display_any_customer_when_none_has_number_starting_with_user_filter_string() throws Exception {
        // when
        customersScreen.userEntersNumberFilterText("99");

        // then
        customersScreen.displaysNoCustomers();
    }

    @Test
    public void should_only_display_customers_whose_name_contains_user_filter_string() throws Exception {
        // when
        customersScreen.userEntersNameFilterText("o");

        // then
        customersScreen.displaysCustomers("John Doe", "Zaphod Beeblebox");
    }

    @Test
    public void should_only_display_customer_whose_name_contains_user_filter_string() throws Exception {
        // when
        customersScreen.userEntersNameFilterText("ohn");

        // then
        customersScreen.displaysCustomers("John Doe");
    }

    @Test
    public void should_not_display_any_customer_when_none_has_name_containing_user_filter_string() throws Exception {
        // when
        customersScreen.userEntersNameFilterText("xxx");

        // then
        customersScreen.displaysNoCustomers();
    }

    @Test
    public void should_display_customer_without_rented_movies() throws Exception {
        customersScreen.displaysCustomer().withAccountNumber("11111").withRentedMovies(0);
    }

    @Test
    public void should_display_customer_with_rented_movies() throws Exception {
        // given: customer 11111 rented 2 movies
        newRental().customerNumber("11111").movieCode("AVENGERS2012").rentalDate(new LocalDate()).notReturned().insert();
        newRental().customerNumber("11111").movieCode("THEDARKKNI2012").rentalDate(new LocalDate()).notReturned().insert();

        // then
        customersScreen.displaysCustomer().withAccountNumber("11111").withRentedMovies(2);
    }

    @Test
    public void should_display_customer_without_late_returns() throws Exception {
        // given: customer 11111 rented 1 movie that is not yet to be returned
        LocalDate today = new LocalDate();
        newRental().customerNumber("11111").movieCode("AVENGERS2012").rentalDate(today).notReturned().insert();

        // then
        customersScreen.displaysCustomer().withAccountNumber("11111").withLateReturns(0);
    }

    @Test
    public void should_display_customer_with_late_returns() throws Exception {
        // given: customer 11111 rented 2 movies, 1 one them being a late return
        LocalDate today = new LocalDate();
        LocalDate twoMonthsBeforeToday = today.minusMonths(2);
        newRental().customerNumber("11111").movieCode("AVENGERS2012").rentalDate(twoMonthsBeforeToday).notReturned().insert();
        newRental().customerNumber("11111").movieCode("THEDARKKNI2012").rentalDate(today).notReturned().insert();

        // then
        customersScreen.displaysCustomer().withAccountNumber("11111").withLateReturns(1);
    }

    @Test
    public void should_propose_actions_for_selected_customer() throws Exception {
        // when
        customersScreen.selectCustomer("22222");

        // then
        customersScreen.proposesActions("Rent Movies...", "Return Movies...", "Past Rentals...");
    }

    private String[] namesOfCustomersInDatabase() {
        return transform(selectAllPropertiesFromCustomerTable(), new Function<Customer, String>() {
            public String apply(Customer customer) {
                return customer.getName();
            }
        }).toArray(new String[0]);
    }
}
