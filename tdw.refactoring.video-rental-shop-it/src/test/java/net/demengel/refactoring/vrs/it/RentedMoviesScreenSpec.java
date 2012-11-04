package net.demengel.refactoring.vrs.it;

import static com.google.common.collect.Lists.transform;
import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.newRental;
import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.selectAllPropertiesFromRentalTable;
import net.demengel.refactoring.vrs.bean.Rental;
import net.demengel.refactoring.vrs.it.driver.RentedMoviesScreen;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Function;

public class RentedMoviesScreenSpec extends VideoRentalStoreIntegrationTestCase {

    RentedMoviesScreen rentedMoviesScreen;

    @Before
    public void rentSomeMoviesAndShowAvailableMoviesScreen() throws Exception {
        rentSomeMovies();

        rentedMoviesScreen = application().showRentedMoviesScreen();
    }

    private void rentSomeMovies() {
        newRental().customerNumber("11111").movieCode("AVENGERS2012").rentalDate(new LocalDate()).notReturned().insert();
        newRental().customerNumber("11111").movieCode("THEDARKKNI2012").rentalDate(new LocalDate()).notReturned().insert();
        newRental().customerNumber("22222").movieCode("SOULKITCHE2009").rentalDate(new LocalDate()).notReturned().insert();
    }

    @Test
    public void should_display_all_rented_movies_when_not_filtered() throws Exception {
        // forces display of rentals that have just been added
        rentedMoviesScreen.resetFilter();
        rentedMoviesScreen.displaysMovies(codesOfRentedMovies());
    }

    @Test
    public void should_only_display_movies_which_titles_contain_user_filter_string() throws Exception {
        // when
        rentedMoviesScreen.userEntersFilterText("he");

        // then
        rentedMoviesScreen.displaysMovies("THEDARKKNI2012", "SOULKITCHE2009");
    }

    @Test
    public void should_only_display_one_movie_which_title_contain_user_filter_string() throws Exception {
        // when
        rentedMoviesScreen.userEntersFilterText("aven");

        // then
        rentedMoviesScreen.displaysMovies("AVENGERS2012");
    }

    @Test
    public void should_not_display_any_movie_when_none_contain_user_filter_string() throws Exception {
        // when
        rentedMoviesScreen.userEntersFilterText("some unknown movie");

        // then
        rentedMoviesScreen.displaysNoMovies();
    }

    @Ignore("TODO")
    @Test
    public void should_implement_more_tests() throws Exception {
        // tests are missing to cover all the features of this screen!
    }

    private String[] codesOfRentedMovies() {
        return transform(selectAllPropertiesFromRentalTable(), new Function<Rental, String>() {
            public String apply(Rental rental) {
                return rental.getMovieCode();
            }
        }).toArray(new String[0]);
    }
}
