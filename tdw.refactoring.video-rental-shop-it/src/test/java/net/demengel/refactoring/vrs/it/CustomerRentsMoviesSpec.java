package net.demengel.refactoring.vrs.it;

import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.selectAllPropertiesFromRentalTableWhereCustomerNumberIsEqualTo;
import static org.fest.assertions.Assertions.assertThat;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Rental;
import net.demengel.refactoring.vrs.it.driver.CustomersScreen;
import net.demengel.refactoring.vrs.it.driver.RentMoviesScreen;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CustomerRentsMoviesSpec extends VideoRentalStoreIntegrationTestCase {

    private static final long ONE_SECOND = 1000;

    CustomersScreen customersScreen;

    @Before
    public void showAvailableMoviesScreen() throws Exception {
        customersScreen = application().showCustomersScreen();
    }

    @Test
    public void should_not_compute_total_rental_price_when_no_movies_added_to_cart() throws Exception {
        // given
        RentMoviesScreen rentMoviesScreen = customersScreen.showRentMoviesScreenForCustomer("11111");

        // then
        rentMoviesScreen.doesNotDisplayTotalPrice();
    }

    @Test
    public void should_pre_compute_total_rental_price_for_movies_added_to_cart__one_movie() throws Exception {
        // given
        RentMoviesScreen rentMoviesScreen = customersScreen.showRentMoviesScreenForCustomer("11111");

        // when
        rentMoviesScreen.userScansMovieCode("THEDARKKNI2012");

        // then
        rentMoviesScreen.displaysTotalPrice("42.0");
    }

    @Test
    public void should_pre_compute_total_rental_price_for_movies_added_to_cart__two_movies() throws Exception {
        // given
        RentMoviesScreen rentMoviesScreen = customersScreen.showRentMoviesScreenForCustomer("11111");

        // when
        rentMoviesScreen.userScansMovieCode("THEDARKKNI2012");
        rentMoviesScreen.userScansMovieCode("SOULKITCHE2009");

        // then
        rentMoviesScreen.displaysTotalPrice("43.3");
    }

    @Test
    public void should_save_rentals_into_db_when_user_validates_cart() throws Exception {
        // given
        RentMoviesScreen rentMoviesScreen = customersScreen.showRentMoviesScreenForCustomer("11111");

        // when
        rentMoviesScreen.userScansMovieCode("THEDARKKNI2012");
        rentMoviesScreen.userScansMovieCode("SOULKITCHE2009");
        rentMoviesScreen.userValidatesCart();

        // then
        List<Rental> customerRentals = selectAllPropertiesFromRentalTableWhereCustomerNumberIsEqualTo("11111");

        assertThat(customerRentals).hasSize(2);

        Rental firstRental = customerRentals.get(0);
        assertThat(firstRental.getMovieCode()).isEqualTo("THEDARKKNI2012");
        assertJustRented(firstRental);

        Rental secondRental = customerRentals.get(1);
        assertThat(secondRental.getMovieCode()).isEqualTo("SOULKITCHE2009");
        assertJustRented(secondRental);
    }

    @Test
    public void should_create_previsional_invoice_when_user_validates_cart() throws Exception {
        // given
        RentMoviesScreen rentMoviesScreen = customersScreen.showRentMoviesScreenForCustomer("11111");

        // when
        rentMoviesScreen.userScansMovieCode("THEDARKKNI2012");
        rentMoviesScreen.userScansMovieCode("SOULKITCHE2009");
        rentMoviesScreen.userValidatesCart();

        // then
        assertThat(findLastInvoiceFile()).hasSameContentAs(expectedFileForThisTest());
    }

    @After
    public void deleteInvoiceFiles() {
        for (File f : new File(".").listFiles(new InvoiceFileFilter())) {
            f.delete();
        }
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

    private static void assertJustRented(Rental rental) {
        assertIsNow(rental.getRentalDate());
        assertThat(rental.getReturnDate()).isNull();
    }

    private static void assertIsNow(Date date) {
        // how would you solve the 'time problem'?
        long now = System.currentTimeMillis();
        assertThat(date.getTime()).isGreaterThan(now - ONE_SECOND).isLessThan(now);
    }
}
