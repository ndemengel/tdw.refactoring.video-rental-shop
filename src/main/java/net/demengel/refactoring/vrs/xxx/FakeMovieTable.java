package net.demengel.refactoring.vrs.xxx;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static net.demengel.refactoring.vrs.xxx.FakeDbUtils.parseDate;

import java.util.HashSet;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Movie;

import org.joda.time.LocalDate;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * Fake MOVIE table in a relational DataBase.
 */
public class FakeMovieTable {

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
                        .cast("Louis de Funès", "Jean Carmet", "Jacques Villeret").ownedQuantity(1).forcedPrice(1.2).build()
                );
    }

    public static List<Movie> selectAllPropertiesFromMovieTable() {
        return newArrayList(copyMovies());
    }

    private static List<Movie> copyMovies() {
        return transform(ALL_MOVIES, new Function<Movie, Movie>() {
            public Movie apply(Movie in) {
                Movie out = new Movie();
                out.setCast(newHashSet(in.getCast()));
                out.setCode(in.getCode());
                out.setCountry(in.getCountry());
                out.setDirector(in.getDirector());
                out.setDuration(in.getDuration());
                out.setForcedPrice(in.getForcedPrice());
                out.setGenres(newHashSet(in.getGenres()));
                out.setOwnedQuantity(in.getOwnedQuantity());
                out.setReleaseDate(in.getReleaseDate());
                out.setRentingStart(in.getRentingStart());
                out.setTitle(in.getTitle());
                out.setWriters(newHashSet(in.getWriters()));
                return out;
            }
        });
    }

    public static List<Movie> selectAllPropertiesFromMovieTableWhereTitleContains(final String titleContents) {
        return newArrayList(filter(copyMovies(), new Predicate<Movie>() {
            public boolean apply(Movie movie) {
                return movie.getTitle().contains(titleContents);
            }
        }));
    }

    public static List<Movie> selectAllPropertiesFromMovieTableWhereCodeIn(final List<String> movieCodes) {
        return newArrayList(filter(copyMovies(), new Predicate<Movie>() {
            public boolean apply(Movie movie) {
                return movieCodes.contains(movie.getCode());
            }
        }));
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

        public MovieBuilder forcedPrice(double price) {
            movie.setForcedPrice(price);
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
