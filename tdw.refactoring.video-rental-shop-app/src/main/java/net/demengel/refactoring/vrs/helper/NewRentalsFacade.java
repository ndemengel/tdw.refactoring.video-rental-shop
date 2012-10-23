package net.demengel.refactoring.vrs.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.bean.Rental;
import net.demengel.refactoring.vrs.dao.RentalDao;
import net.demengel.refactoring.vrs.dao.Transaction;
import net.demengel.refactoring.vrs.dao.TransactionContext;

/**
 * A facade providing operations for new rentals.
 * 
 * @author GOD 16 jan. 2012
 */
public class NewRentalsFacade extends RentalsFacade {

    public NewRentalsFacade(Customer pSelectedCustomer, List<Movie> pMoviesToRent, Date pRentalDate) {
        super(pSelectedCustomer, pMoviesToRent, pRentalDate);
    }

    /**
     * 
     * @throws Exception
     */
    public void saveRentals() throws Exception {
        Transaction transaction = TransactionContext.getInstance().newTransaction();
        try {

            mRentals = new ArrayList<Rental>(mRentedMovies.size());
            for (Movie m : mRentedMovies) {
                Rental r = new Rental();
                r.setCustomerNumber(mSelectedCustomer.getAccountNumber());
                r.setMovieCode(m.getCode());
                r.setRentalDate(mRentalDate);
                mRentals.add(r);

                RentalDao.getInstance().create(r, transaction);
                CustomerHelper.getInstance().flushRentalsCache();
            }

            TransactionContext.getInstance().commit(transaction);
        } catch (Exception e) {
            TransactionContext.getInstance().rollback(transaction);
            throw e;
        }
        // TODO Auto-generated method stub
        printInvoice();
    }
}
