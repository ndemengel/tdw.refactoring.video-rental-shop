package net.demengel.refactoring.vrs.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.bean.Renting;
import net.demengel.refactoring.vrs.dao.CustomerDao;
import net.demengel.refactoring.vrs.dao.MovieDao;
import net.demengel.refactoring.vrs.dao.ReferentialDao;
import net.demengel.refactoring.vrs.dao.RentingDao;
import net.demengel.refactoring.vrs.dao.Transaction;
import net.demengel.refactoring.vrs.dao.TransactionContext;
import net.demengel.refactoring.vrs.util.ReferentialProperties;

/**
 * A facade providing operations for existing rentings.
 * 
 * @author GOD 16 jan. 2012
 */
public class ExistingRentingsFacade extends RentingsFacade implements ReferentialProperties {

    public ExistingRentingsFacade(Customer pSelectedCustomer, List<Renting> pRentings, Date pReturnDate) {
        super(pSelectedCustomer, getMovies(pRentings), pRentings, pReturnDate);
    }

    // put there because Java does not allow writing code before calling 'super'
    private static List<Movie> getMovies(List<Renting> pRentings) {
        List<String> codes = new ArrayList<String>(pRentings.size());
        for (Renting lRenting : pRentings) {
            codes.add(lRenting.getMovieCode());
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
            Integer maxRentingDays = new Integer(ReferentialDao.getInstance().get(MAX_RENTING_DAYS));

            for (Renting renting : mRentings) {
                Movie lMovie = mRentedMovies.get(i++);

                if (mReturnDate.getTime() - renting.getRentingDate().getTime() < (long) maxRentingDays * 24 * 3600 * 1000) {
                    // if movie has been rent for less than 3 months
                    if (renting.getRentingDate().getTime() - lMovie.getRentingStart().getTime() < 90L * 24 * 3600 * 1000) {
                        newCredits += 50;
                    }
                    // else, if movie has been rent for less than 1 year
                    else if (renting.getRentingDate().getTime() - lMovie.getRentingStart().getTime() < 365L * 24 * 3600 * 1000) {
                        newCredits += 30;
                    }
                    // else, if movie has been rent for more than 1 year
                    else {
                        newCredits += 15;
                    }
                }

                renting.setReturnDate(mReturnDate);
                RentingDao.getInstance().update(renting, transaction);
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
