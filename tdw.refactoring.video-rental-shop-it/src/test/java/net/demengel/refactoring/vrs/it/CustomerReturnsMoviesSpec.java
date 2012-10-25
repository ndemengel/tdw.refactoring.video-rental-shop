package net.demengel.refactoring.vrs.it;

import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.newRental;
import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.selectAllPropertiesFromRentalTableWhereCustomerNumberIsEqualTo;
import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Rental;
import net.demengel.refactoring.vrs.it.driver.CustomersScreen;
import net.demengel.refactoring.vrs.it.driver.ReturnMoviesScreen;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class CustomerReturnsMoviesSpec extends VideoRentalStoreIntegrationTestCase {

    private static final long ONE_SECOND = 1000;

    CustomersScreen customersScreen;
    LocalDate yesterday = new LocalDate().minusDays(1);

    @Before
    public void showAvailableMoviesScreen() throws Exception {
        customersScreen = application().showCustomersScreen();
    }

    @Test
    public void should_display_zero_as_total_rental_price_when_no_rented_movies() throws Exception {
        // given
        ReturnMoviesScreen returnMoviesScreen = customersScreen.showReturnMoviesScreenForCustomer("11111");

        // then
        returnMoviesScreen.displaysTotalPrice("0.0");
    }

    @Test
    public void should_compute_total_rental_price_for_rented_movies__one_movie() throws Exception {
        // given
        newRental().customerNumber("11111").movieCode("THEDARKKNI2012").rentalDate(yesterday).notReturned().insert();

        // when
        ReturnMoviesScreen returnMoviesScreen = customersScreen.showReturnMoviesScreenForCustomer("11111");

        // then
        returnMoviesScreen.displaysTotalPrice("42.0");
    }

    @Test
    public void should_compute_total_rental_price_for_rented_movies__two_movies() throws Exception {
        // given
        newRental().customerNumber("11111").movieCode("THEDARKKNI2012").rentalDate(yesterday).notReturned().insert();
        newRental().customerNumber("11111").movieCode("SOULKITCHE2009").rentalDate(yesterday).notReturned().insert();

        // when
        ReturnMoviesScreen returnMoviesScreen = customersScreen.showReturnMoviesScreenForCustomer("11111");

        // then
        returnMoviesScreen.displaysTotalPrice("43.3");
    }

    @Test
    public void should_update_rentals_info_when_user_validates_return() throws Exception {
        // given
        newRental().customerNumber("11111").movieCode("THEDARKKNI2012").rentalDate(yesterday).notReturned().insert();
        newRental().customerNumber("11111").movieCode("SOULKITCHE2009").rentalDate(yesterday).notReturned().insert();

        // when
        ReturnMoviesScreen returnMoviesScreen = customersScreen.showReturnMoviesScreenForCustomer("11111");
        returnMoviesScreen.userValidatesReturn();

        // then
        List<Rental> customerRentals = selectAllPropertiesFromRentalTableWhereCustomerNumberIsEqualTo("11111");

        assertThat(customerRentals).hasSize(2);

        Rental firstRental = customerRentals.get(0);
        assertThat(firstRental.getMovieCode()).isEqualTo("THEDARKKNI2012");
        assertJustReturned(firstRental);

        Rental secondRental = customerRentals.get(1);
        assertThat(secondRental.getMovieCode()).isEqualTo("SOULKITCHE2009");
        assertJustReturned(secondRental);
    }

    @Test
    public void should_create_invoice_when_user_validates_return() throws Exception {
        // given
        newRental().customerNumber("11111").movieCode("THEDARKKNI2012").rentalDate(yesterday).notReturned().insert();
        newRental().customerNumber("11111").movieCode("SOULKITCHE2009").rentalDate(yesterday).notReturned().insert();

        // when
        ReturnMoviesScreen returnMoviesScreen = customersScreen.showReturnMoviesScreenForCustomer("11111");
        returnMoviesScreen.userValidatesReturn();

        // then
        assertThat(findLastInvoiceFile()).hasSameContentAs(expectedFileForThisTest());
    }

    private File findLastInvoiceFile() {
        List<String> fileNames = asList(new File(".").list(new InvoiceFileFilter()));
        sort(fileNames);
        return fileNames.isEmpty() ? null : new File(fileNames.get(0));
    }

    private File expectedFileForThisTest() throws URISyntaxException {
        String expectedFilePath = "/invoice-" + getClass().getSimpleName() + "." + testName.getMethodName() + ".md";
        return new File(getClass().getResource(expectedFilePath).toURI());
    }

    private static void assertJustReturned(Rental rental) {
        assertIsNow(rental.getReturnDate());
        assertThat(rental.getRentalDate().getTime()).isLessThan(rental.getReturnDate().getTime());
    }

    private static void assertIsNow(Date date) {
        // how would you solve the 'time problem'?
        long now = System.currentTimeMillis();
        assertThat(date.getTime()).isGreaterThan(now - ONE_SECOND).isLessThan(now);
    }
}
