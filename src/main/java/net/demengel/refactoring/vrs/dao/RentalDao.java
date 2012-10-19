package net.demengel.refactoring.vrs.dao;

import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.insertAllPropertiesIntoRentalTable;
import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.selectAllPropertiesFromRentalTableWhereCustomerNumberIsEqualTo;
import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.selectAllPropertiesFromRentalTableWhereReturnDateIsNull;
import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.selectAllPropertiesFromRentalTableWhereReturnDateIsNullAndMovieCodeIsEqualTo;
import static net.demengel.refactoring.vrs.xxx.FakeRentalTable.updatesAllPropertiesFromRentalTableForRental;

import java.util.List;

import net.demengel.refactoring.vrs.bean.Rental;

/**
 * Responsible for retrieving rental data
 * 
 * 
 * @author nico 17 oct. 2012
 */
public class RentalDao {

    /** The instance */
    private static RentalDao sInstance;

    /**
     * 
     */
    private RentalDao() {
    }

    /**
     * Returns the instance of RentalDao
     * 
     * @return
     */
    public static synchronized RentalDao getInstance() {
        if (sInstance == null) {
            sInstance = new RentalDao();
        }
        return sInstance;

    }

    /**
     * 
     * @return
     */
    public List<Rental> findCurrentRentals() {
        return selectAllPropertiesFromRentalTableWhereReturnDateIsNull();
    }

    /**
     * Finds current rentals for given movie.
     * 
     * @param p_movieCode
     * @return
     */
    public List<Rental> findCurrentRentalsForMovie(String p_movieCode) {
        return selectAllPropertiesFromRentalTableWhereReturnDateIsNullAndMovieCodeIsEqualTo(p_movieCode);
    }

    /**
     * Finds current rentals for given movie.
     * 
     * @param p_movieCode
     * @return
     */
    public List<Rental> findRentalsForCustomer(String pAccountNumber) {
        return selectAllPropertiesFromRentalTableWhereCustomerNumberIsEqualTo(pAccountNumber);
    }

    /**
     * Saves the given rental, within the given transaction.
     * 
     * @param pRental
     *            the rental to save
     * @param pTransaction
     *            the transaction to use
     */
    public void create(Rental pRental, Transaction pTransaction) {
        insertAllPropertiesIntoRentalTable(pRental, pTransaction);
    }

    /**
     * Updates the given rental, within the given transaction.
     * 
     * @param pRental
     *            the rental to save
     * @param pTransaction
     *            the transaction to use
     */
    public void update(Rental pRental, Transaction pTransaction) {
        updatesAllPropertiesFromRentalTableForRental(pRental, pTransaction);
    }
}
