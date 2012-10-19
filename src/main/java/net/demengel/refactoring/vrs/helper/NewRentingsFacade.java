package net.demengel.refactoring.vrs.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.bean.Renting;
import net.demengel.refactoring.vrs.dao.RentingDao;
import net.demengel.refactoring.vrs.dao.Transaction;
import net.demengel.refactoring.vrs.dao.TransactionContext;

/**
 * A facade providing operations for new rentings.
 * 
 * @author GOD 16 jan. 2012
 */
public class NewRentingsFacade extends RentingsFacade {

    public NewRentingsFacade(Customer pSelectedCustomer, List<Movie> pMoviesToRent, Date pRentingDate) {
        super(pSelectedCustomer, pMoviesToRent, pRentingDate);
    }

    /**
     * 
     * @throws Exception
     */
    public void saveRentings() throws Exception {
        Transaction transaction = TransactionContext.getInstance().newTransaction();
        try {

            mRentings = new ArrayList<Renting>(mRentedMovies.size());
            for (Movie m : mRentedMovies) {
                Renting r = new Renting();
                r.setCustomerNumber(mSelectedCustomer.getAccountNumber());
                r.setMovieCode(m.getCode());
                r.setRentingDate(mRentingDate);
                mRentings.add(r);

                RentingDao.getInstance().create(r, transaction);
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
