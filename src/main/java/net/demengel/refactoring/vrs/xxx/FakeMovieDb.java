package net.demengel.refactoring.vrs.xxx;

import static java.util.Arrays.asList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.joda.time.LocalDate;

import net.demengel.refactoring.vrs.bean.Movie;

public class FakeMovieDb {

    private static final List<Movie> ALL_MOVIES;
    static {
        LocalDate now = new LocalDate();

        ALL_MOVIES = asList(
                movie("BEKINDREWI2008").title("Be Kind Rewind").country("US").releaseDate("2008/01/20").rentingStart("2008/04/26").genres("Comedy", "Drama")
                        .duration(102).director("Michel Gondry").writers("Michel Gondry").cast("Jack Black", "Mos Def", "Danny Glover").ownedQuantity(5)
                        .build(),
                movie("THEDARKNIG2012").title("The Dark Knight Rises").country("US").releaseDate("2012/07/16").rentingStart(now.minusMonths(2))
                        .genres("Action", "Crime", "Thriller").duration(165).director("Christopher Nolan").writers("Jonathan Nolan", "Christopher Nolan")
                        .cast("Christian Bale", "Tom Hardy", "Anne Hathaway").ownedQuantity(21).build(),
                movie("AVENGERS2012").title("Avengers").country("US").releaseDate("2012/04/11").rentingStart(now.minusMonths(5)).genres("Action")
                        .duration(143).director("Joss Whedon").writers("Zak Penn", "Joss Whedon")
                        .cast("Robert Downey Jr.", "Chris Evans", "Scarlett Johansson").ownedQuantity(17).build(),
                movie("SOULKITCH2009").title("Soul Kitchen").country("DE").releaseDate("2009/12/25").rentingStart("2010/08/01").genres("Comedy", "Drama")
                        .duration(99).director("Fatih Akin").writers("Fatih Akin", "Adam Bousdoukos").cast("Adam Bousdoukos", "Moritz Bleibtreu", "Birol Ünel")
                        .ownedQuantity(2).build(),
                movie("LASOUPEAUXCHOUX1981").title("La soupe aux choux").country("FR").releaseDate("1981/12/02").rentingStart("1985/11/05")
                        .genres("Comedy", "Sci-Fi").duration(98).director("Jean Girault").writers("Louis de Funès", "René Fallet")
                        .cast("Louis de Funès", "Jean Carmet", "Jacques Villeret").ownedQuantity(1).build()
                );
    }

    public static List<Movie> getAllMovies() {
        return new ArrayList<Movie>(ALL_MOVIES);
    }

    private static MovieBuilder movie(String code) {
        return new MovieBuilder(code);
    }

    private static class MovieBuilder {

        private Movie movie;

        public MovieBuilder(String code) {
            movie = new Movie();
            movie.setCode(code);
        }

        public Movie build() {
            return movie;
        }

        public MovieBuilder cast(String... stars) {
            movie.setCast(new HashSet<String>(asList(stars)));
            return this;
        }

        public MovieBuilder country(String country) {
            movie.setCountry(country);
            return this;
        }

        public MovieBuilder director(String director) {
            movie.setDirector(director);
            return this;
        }

        public MovieBuilder duration(int duration) {
            movie.setDuration(duration);
            return this;
        }

        public MovieBuilder genres(String... genres) {
            movie.setGenres(new HashSet<String>(asList(genres)));
            return this;
        }

        public MovieBuilder ownedQuantity(int quantity) {
            movie.setOwnedQuantity(quantity);
            return this;
        }

        public MovieBuilder releaseDate(String date) {
            movie.setReleaseDate(parseDate(date));
            return this;
        }

        public MovieBuilder rentingStart(String date) {
            movie.setRentingStart(parseDate(date));
            return this;
        }

        public MovieBuilder rentingStart(LocalDate date) {
            movie.setRentingStart(date.toDate());
            return this;
        }

        private Date parseDate(String date) {
            try {
                return new SimpleDateFormat("yyyy/MM/dd").parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        public MovieBuilder title(String title) {
            movie.setTitle(title);
            return this;
        }

        public MovieBuilder writers(String... writers) {
            movie.setWriters(new HashSet<String>(asList(writers)));
            return this;
        }
    }
}
