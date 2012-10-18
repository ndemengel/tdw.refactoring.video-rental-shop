package net.demengel.refactoring.vrs.xxx;

import static com.google.common.base.Predicates.and;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;
import static java.util.Arrays.asList;
import static net.demengel.refactoring.vrs.xxx.FakeDbUtils.parseDate;

import java.util.List;

import net.demengel.refactoring.vrs.bean.Renting;

import org.joda.time.LocalDate;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * Fake RENTING table in a relational DataBase.
 */
public class FakeRentingTable {

    private static final List<Renting> ALL_RENTINGS;
    static {
        LocalDate now = new LocalDate();

        ALL_RENTINGS = asList(
                renting().customerNumber("11111").movieCode("BEKINDREWI2008").rentingDate("2010/06/15").returnDate("2010/06/16").build(),
                renting().customerNumber("11111").movieCode("THEDARKNIG2012").rentingDate(now.minusDays(23)).returnDate(now.minusDays(21)).build(),
                renting().customerNumber("11111").movieCode("LASOUPEAUXCHOUX1981").rentingDate(now.minusDays(11)).notReturned().build(),
                renting().customerNumber("22222").movieCode("THEDARKNIG2012").rentingDate(now.minusDays(2)).notReturned().build(),
                renting().customerNumber("22222").movieCode("AVENGERS2012").rentingDate(now.minusDays(2)).notReturned().build(),
                renting().customerNumber("22222").movieCode("SOULKITCH2009").rentingDate(now.minusDays(2)).notReturned().build()
                );
    }

    public static List<Renting> selectAllPropertiesFromRentingTableWhereReturnDateIsNull() {
        return newArrayList(filter(copyRentings(), nullReturnDate()));
    }

    private static List<Renting> copyRentings() {
        return transform(ALL_RENTINGS, new Function<Renting, Renting>() {
            public Renting apply(Renting in) {
                Renting out = new Renting();
                out.setCustomerNumber(in.getCustomerNumber());
                out.setMovieCode(in.getMovieCode());
                out.setRentingDate(in.getRentingDate());
                out.setReturnDate(in.getReturnDate());
                return out;
            }
        });
    }

    private static Predicate<Renting> nullReturnDate() {
        return new Predicate<Renting>() {
            public boolean apply(Renting renting) {
                return renting.getReturnDate() == null;
            }
        };
    }

    public static List<Renting> selectAllPropertiesFromRentingTableWhereReturnDateIsNullAndMovieCodeIsEqualTo(final String movieCode) {
        return newArrayList(filter(copyRentings(), and(nullReturnDate(), movieCodeEqualTo(movieCode))));
    }

    private static Predicate<Renting> movieCodeEqualTo(final String movieCode) {
        return new Predicate<Renting>() {
            public boolean apply(Renting renting) {
                return renting.getMovieCode().equals(movieCode);
            }
        };
    }

    public static List<Renting> selectAllPropertiesFromRentingTableWhereCustomerNumberIsEqualTo(String customerNumber) {
        return newArrayList(filter(copyRentings(), customerNumberEqualTo(customerNumber)));
    }

    private static Predicate<Renting> customerNumberEqualTo(final String customerNumber) {
        return new Predicate<Renting>() {
            public boolean apply(Renting renting) {
                return renting.getCustomerNumber().equals(customerNumber);
            }
        };
    }

    private static RentingBuilder renting() {
        return new RentingBuilder();
    }

    private static class RentingBuilder {

        private Renting renting = new Renting();

        public Renting build() {
            return renting;
        }

        public RentingBuilder customerNumber(String number) {
            renting.setCustomerNumber(number);
            return this;
        }

        public RentingBuilder movieCode(String code) {
            renting.setMovieCode(code);
            return this;
        }

        public RentingBuilder notReturned() {
            renting.setReturnDate(null);
            return this;
        }

        public RentingBuilder rentingDate(String date) {
            renting.setRentingDate(parseDate(date));
            return this;
        }

        public RentingBuilder rentingDate(LocalDate date) {
            renting.setRentingDate(date.toDate());
            return this;
        }

        public RentingBuilder returnDate(String date) {
            renting.setReturnDate(parseDate(date));
            return this;
        }

        public RentingBuilder returnDate(LocalDate date) {
            renting.setReturnDate(date.toDate());
            return this;
        }
    }
}
