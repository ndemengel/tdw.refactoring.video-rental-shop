package net.demengel.refactoring.vrs.dao;

import static net.demengel.refactoring.vrs.xxx.FakeRentingTable.insertAllPropertiesIntoRentingTable;
import static net.demengel.refactoring.vrs.xxx.FakeRentingTable.selectAllPropertiesFromRentingTableWhereCustomerNumberIsEqualTo;
import static net.demengel.refactoring.vrs.xxx.FakeRentingTable.selectAllPropertiesFromRentingTableWhereReturnDateIsNull;
import static net.demengel.refactoring.vrs.xxx.FakeRentingTable.selectAllPropertiesFromRentingTableWhereReturnDateIsNullAndMovieCodeIsEqualTo;
import static net.demengel.refactoring.vrs.xxx.FakeRentingTable.updatesAllPropertiesFromRentingTableForRenting;

import java.util.List;

import net.demengel.refactoring.vrs.bean.Renting;

/**
 * Responsible for retrieving renting data
 * 
 * 
 * @author nico 17 oct. 2012
 */
public class RentingDao {

    /** The instance */
    private static RentingDao sInstance;

    /**
     * 
     */
    private RentingDao() {
    }

    /**
     * Returns the instance of RentingDao
     * 
     * @return
     */
    public static synchronized RentingDao getInstance() {
        if (sInstance == null) {
            sInstance = new RentingDao();
        }
        return sInstance;

    }

    /**
     * 
     * @return
     */
    public List<Renting> findCurrentRentings() {
        return selectAllPropertiesFromRentingTableWhereReturnDateIsNull();
    }

    /**
     * Finds current rentings for given movie.
     * 
     * @param p_movieCode
     * @return
     */
    public List<Renting> findCurrentRentingsForMovie(String p_movieCode) {
        return selectAllPropertiesFromRentingTableWhereReturnDateIsNullAndMovieCodeIsEqualTo(p_movieCode);
    }

    /**
     * Finds current rentings for given movie.
     * 
     * @param p_movieCode
     * @return
     */
    public List<Renting> findRentingsForCustomer(String pAccountNumber) {
        return selectAllPropertiesFromRentingTableWhereCustomerNumberIsEqualTo(pAccountNumber);
    }

    /**
     * Saves the given renting, within the given transaction.
     * 
     * @param pRenting
     *            the renting to save
     * @param pTransaction
     *            the transaction to use
     */
    public void create(Renting pRenting, Transaction pTransaction) {
        insertAllPropertiesIntoRentingTable(pRenting, pTransaction);
    }

    /**
     * Updates the given renting, within the given transaction.
     * 
     * @param pRenting
     *            the renting to save
     * @param pTransaction
     *            the transaction to use
     */
    public void update(Renting pRenting, Transaction pTransaction) {
        updatesAllPropertiesFromRentingTableForRenting(pRenting, pTransaction);
    }
}
