package net.demengel.refactoring.vrs.it;

import static com.google.common.collect.Lists.transform;
import static net.demengel.refactoring.vrs.xxx.FakeMovieTable.selectAllPropertiesFromMovieTable;
import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.newRental;
import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.it.driver.AvailableMoviesScreen;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;

public class AvailableMoviesScreenSpec extends VideoRentalStoreIntegrationTestCase {

    AvailableMoviesScreen availableMoviesScreen;

    @Before
    public void showAvailableMoviesScreen() throws Exception {
        availableMoviesScreen = application().showAvailableMoviesScreen();
    }

    @Test
    public void should_display_all_movies_when_not_filtered() throws Exception {
        availableMoviesScreen.displaysMovies(titlesOfMoviesInDatabase());
    }

    @Test
    public void should_display_right_price_for_movies_younger_than_3_months() throws Exception {
        availableMoviesScreen.displaysMovie().withTitle("The Dark Knight Rises").withPrice("42.0");
    }

    @Test
    public void should_display_right_price_for_movies_younger_than_1_year() throws Exception {
        availableMoviesScreen.displaysMovie().withTitle("Avengers").withPrice("17.2");
    }

    @Test
    public void should_display_right_price_for_movies_older_than_1_year() throws Exception {
        availableMoviesScreen.displaysMovie().withTitle("Be Kind Rewind").withPrice("1.3");
    }

    @Test
    public void should_display_total_owned_quantity_for_movies_that_are_not_rented() throws Exception {
        availableMoviesScreen.displaysMovie().withTitle("Be Kind Rewind").withAvailableQuantity(5);
    }

    @Test
    public void should_only_display_available_quantity_for_movies_that_are_rented() throws Exception {
        // given: "Be Kind Rewind" is rented twice
        newRental().customerNumber("11111").movieCode("BEKINDREWI2008").rentalDate(new LocalDate()).notReturned().insert();
        newRental().customerNumber("22222").movieCode("BEKINDREWI2008").rentalDate(new LocalDate()).notReturned().insert();

        // then
        availableMoviesScreen.displaysMovie().withTitle("Be Kind Rewind").withAvailableQuantity(3);
    }

    @Test
    public void should_only_display_movies_which_titles_contain_user_filter_string() throws Exception {
        // when
        availableMoviesScreen.userEntersFilterText("he");

        // then
        availableMoviesScreen.displaysMovies("The Dark Knight Rises", "Soul Kitchen");
    }

    @Test
    public void should_only_display_one_movie_which_title_contain_user_filter_string() throws Exception {
        // when
        availableMoviesScreen.userEntersFilterText("dark knig");

        // then
        availableMoviesScreen.displaysMovies("The Dark Knight Rises");
    }

    @Test
    public void should_not_display_any_movie_when_none_contain_user_filter_string() throws Exception {
        // when
        availableMoviesScreen.userEntersFilterText("some unknown movie");

        // then
        availableMoviesScreen.displaysNoMovies();
    }

    private String[] titlesOfMoviesInDatabase() {
        return transform(selectAllPropertiesFromMovieTable(), new Function<Movie, String>() {
            public String apply(Movie movie) {
                return movie.getTitle();
            }
        }).toArray(new String[0]);
    }
}
