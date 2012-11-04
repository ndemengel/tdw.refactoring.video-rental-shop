package net.demengel.refactoring.vrs.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.bean.Rental;
import net.demengel.refactoring.vrs.dao.CustomerDao;
import net.demengel.refactoring.vrs.dao.MovieDao;
import net.demengel.refactoring.vrs.dao.ReferentialDao;
import net.demengel.refactoring.vrs.dao.RentalDao;
import net.demengel.refactoring.vrs.dao.Transaction;
import net.demengel.refactoring.vrs.dao.TransactionContext;
import net.demengel.refactoring.vrs.util.ReferentialProperties;

/**
 * A facade providing operations for existing rentals.
 * 
 * @author GOD 16 jan. 2012
 */
public class ExistingRentalsFacade extends RentalsFacade implements ReferentialProperties {

    public ExistingRentalsFacade(Customer pSelectedCustomer, List<Rental> pRentals, Date pReturnDate) {
        super(pSelectedCustomer, getMovies(pRentals), pRentals, pReturnDate);
    }

    // put there because Java does not allow writing code before calling 'super'
    private static List<Movie> getMovies(List<Rental> pRentals) {
        List<String> codes = new ArrayList<String>(pRentals.size());
        for (Rental lRental : pRentals) {
            codes.add(lRental.getMovieCode());
        }
        List lMovies = MovieDao.getInstance().getMoviesByCodes(codes);
        return (List<Movie>) lMovies;
    }

    /**
     * 
     * @throws Exception
     */
    public void saveReturns() throws Exception {
        Transaction transaction = TransactionContext.getInstance().newTransaction();
        try {
            int i = 0;
            int newCredits = 0;
            Integer maxRentalDays = new Integer(ReferentialDao.getInstance().get(MAX_RENTAL_DAYS));

            for (Rental rental : mRentals) {
                Movie lMovie = mRentedMovies.get(i++);

                if (mReturnDate.getTime() - rental.getRentalDate().getTime() < (long) maxRentalDays * 24 * 3600 * 1000) {
                    // if movie has been proposed for less than 3 months
                    if (rental.getRentalDate().getTime() - lMovie.getRentalReleaseDate().getTime() < 90L * 24 * 3600 * 1000) {
                        newCredits += 50;
                    }
                    // else, if movie has been proposed for less than 1 year
                    else if (rental.getRentalDate().getTime() - lMovie.getRentalReleaseDate().getTime() < 365L * 24 * 3600 * 1000) {
                        newCredits += 30;
                    }
                    // else, if movie has been proposed for more than 1 year
                    else {
                        newCredits += 15;
                    }
                }

                rental.setReturnDate(mReturnDate);
                RentalDao.getInstance().update(rental, transaction);
                CustomerHelper.getInstance().flushRentalsCache();
            }

            // updates customer credits
            mSelectedCustomer.setCredits(mSelectedCustomer.getCredits() + newCredits);
            CustomerDao.getInstance().update(mSelectedCustomer, transaction);

            TransactionContext.getInstance().commit(transaction);
        } catch (Exception e) {
            TransactionContext.getInstance().rollback(transaction);
            throw e;
        }
        printInvoice();
    }
}
