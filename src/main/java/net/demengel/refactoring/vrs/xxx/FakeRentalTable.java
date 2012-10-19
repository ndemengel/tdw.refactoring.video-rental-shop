package net.demengel.refactoring.vrs.xxx;

import static com.google.common.base.Predicates.and;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static net.demengel.refactoring.vrs.xxx.FakeDbUtils.doInTransaction;
import static net.demengel.refactoring.vrs.xxx.FakeDbUtils.parseDate;

import java.util.List;

import net.demengel.refactoring.vrs.bean.Rental;
import net.demengel.refactoring.vrs.dao.Transaction;

import org.joda.time.LocalDate;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

/**
 * Fake RENTAL table in a relational database. Static methods of this class represents SQL queries made to the database.
 */
public class FakeRentalTable {

    private static final List<Rental> ALL_RENTALS;
    static {
        LocalDate now = new LocalDate();

        ALL_RENTALS = newArrayList(
                rental().customerNumber("11111").movieCode("BEKINDREWI2008").rentalDate("2010/06/15").returnDate("2010/06/16").build(),
                rental().customerNumber("11111").movieCode("THEDARKKNI2012").rentalDate(now.minusDays(23)).returnDate(now.minusDays(21)).build(),
                rental().customerNumber("11111").movieCode("LASOUPEAUX1981").rentalDate(now.minusDays(11)).notReturned().build(),
                rental().customerNumber("22222").movieCode("THEDARKKNI2012").rentalDate(now.minusDays(2)).notReturned().build(),
                rental().customerNumber("22222").movieCode("AVENGERS2012").rentalDate(now.minusDays(2)).notReturned().build(),
                rental().customerNumber("22222").movieCode("SOULKITCH2009").rentalDate(now.minusDays(2)).notReturned().build()
                );
    }

    public static List<Rental> selectAllPropertiesFromRentalTableWhereReturnDateIsNull() {
        return copy(filter(ALL_RENTALS, nullReturnDate()));
    }

    private static List<Rental> copy(Iterable<Rental> rentals) {
        return newArrayList(transform(rentals, new Function<Rental, Rental>() {
            public Rental apply(Rental in) {
                Rental out = new Rental();
                out.setCustomerNumber(in.getCustomerNumber());
                out.setMovieCode(in.getMovieCode());
                out.setRentalDate(in.getRentalDate());
                out.setReturnDate(in.getReturnDate());
                return out;
            }
        }));
    }

    private static Predicate<Rental> nullReturnDate() {
        return new Predicate<Rental>() {
            public boolean apply(Rental rental) {
                return rental.getReturnDate() == null;
            }
        };
    }

    public static List<Rental> selectAllPropertiesFromRentalTableWhereReturnDateIsNullAndMovieCodeIsEqualTo(final String movieCode) {
        return copy(filter(ALL_RENTALS, and(nullReturnDate(), movieCodeEqualTo(movieCode))));
    }

    private static Predicate<Rental> movieCodeEqualTo(final String movieCode) {
        return new Predicate<Rental>() {
            public boolean apply(Rental rental) {
                return rental.getMovieCode().equals(movieCode);
            }
        };
    }

    public static List<Rental> selectAllPropertiesFromRentalTableWhereCustomerNumberIsEqualTo(String customerNumber) {
        return copy(filter(ALL_RENTALS, customerNumberEqualTo(customerNumber)));
    }

    private static Predicate<Rental> customerNumberEqualTo(final String customerNumber) {
        return new Predicate<Rental>() {
            public boolean apply(Rental rental) {
                return rental.getCustomerNumber().equals(customerNumber);
            }
        };
    }

    public static void insertAllPropertiesIntoRentalTable(final Rental rental, Transaction transaction) {
        doInTransaction(transaction, new Runnable() {
            @Override
            public void run() {
                ALL_RENTALS.add(rental);
            }
        });
    }

    public static void updatesAllPropertiesFromRentalTableForRental(final Rental rental, Transaction transaction) {
        doInTransaction(transaction, new Runnable() {
            @Override
            public void run() {
                Rental rentalToUpdate = find(ALL_RENTALS, and(movieCodeEqualTo(rental.getMovieCode()), customerNumberEqualTo(rental.getCustomerNumber())));
                rentalToUpdate.setRentalDate(rental.getRentalDate());
                rentalToUpdate.setReturnDate(rental.getReturnDate());
            }
        });
    }

    private static RentalBuilder rental() {
        return new RentalBuilder();
    }

    /**
     * Note: the attribute movieTitle of Rental objects does not match any column in the RENTAL table, therefore there is no building method
     * for this attribute.
     */
    private static class RentalBuilder {

        private Rental rental = new Rental();

        public Rental build() {
            return rental;
        }

        public RentalBuilder customerNumber(String number) {
            rental.setCustomerNumber(number);
            return this;
        }

        public RentalBuilder movieCode(String code) {
            rental.setMovieCode(code);
            return this;
        }

        public RentalBuilder notReturned() {
            rental.setReturnDate(null);
            return this;
        }

        public RentalBuilder rentalDate(String date) {
            rental.setRentalDate(parseDate(date));
            return this;
        }

        public RentalBuilder rentalDate(LocalDate date) {
            rental.setRentalDate(date.toDate());
            return this;
        }

        public RentalBuilder returnDate(String date) {
            rental.setReturnDate(parseDate(date));
            return this;
        }

        public RentalBuilder returnDate(LocalDate date) {
            rental.setReturnDate(date.toDate());
            return this;
        }
    }
}
