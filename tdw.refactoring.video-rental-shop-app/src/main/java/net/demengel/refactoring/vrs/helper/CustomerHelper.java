package net.demengel.refactoring.vrs.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Rental;
import net.demengel.refactoring.vrs.dao.ReferentialDao;
import net.demengel.refactoring.vrs.dao.RentalDao;
import net.demengel.refactoring.vrs.util.LRUCache;
import net.demengel.refactoring.vrs.util.ReferentialProperties;

public class CustomerHelper implements ReferentialProperties {

    private static final Object LOCK = new Object();
    private static CustomerHelper s_instance;

    private LRUCache<String, List<Rental>> rentalsByCustomerId = new LRUCache<String, List<Rental>>(4000); // TODO 2011/02/13 retrieve
                                                                                                              // cache size from system
                                                                                                              // property
    private RentalDao m_rentalDAO;

    public static CustomerHelper getInstance() {
        if (s_instance == null) {

            // 2010/11/06 FIX: Addition of 'synchronized' to avoid instantiating the singleton twice
            synchronized (LOCK) {

                if (s_instance == null) {
                    s_instance = new CustomerHelper();
                }
            }

        }

        return s_instance;
    }
    
    private CustomerHelper()
    {
        m_rentalDAO= RentalDao.getInstance();
    }

    /**
     * Get current rentals for customer.
     * 
     * @param pAccountNumber the account number of the customer
     * @return the current retnings
     */
    public List<Rental> getCurrentRentalsForCustomer(String pAccountNumber) {
        // 2011/02/13 cache added to improve performance
        List<Rental> lsit;
        if (rentalsByCustomerId.containsKey(pAccountNumber)) {
            lsit = rentalsByCustomerId.get(pAccountNumber);
            
        }
        else {
            lsit = m_rentalDAO.findRentalsForCustomer(pAccountNumber);
        }
        
        List<Rental> lcurRentals = new ArrayList<Rental>(lsit);
        Iterator<Rental> iterator = lcurRentals.iterator();
        while (iterator.hasNext()) {
            Rental r = iterator.next();
            if (r.getReturnDate() != null) {
                iterator.remove();
            }
        }
        return lcurRentals;
    }
    
    /**
     * Get late returns for customer.
     * 
     * @param pAccountNumber the account number of the customer
     * @return the current retnings
     */
    public List<Rental> getLateReturnsForCustomer(String pAccountNumber) {
        // 2011/02/13 cache added to improve performance
        List<Rental> lsit;
        if (rentalsByCustomerId.containsKey(pAccountNumber)) {
            lsit = rentalsByCustomerId.get(pAccountNumber);
            
        }
        else {
            lsit = m_rentalDAO.findRentalsForCustomer(pAccountNumber);
        }
        List<Rental> result = new ArrayList<Rental>();
        Integer maxRentalDays = new Integer(ReferentialDao.getInstance().get(MAX_RENTAL_DAYS));
        for (Rental lRental : lsit) {
            if (lRental != null
                    && lRental.getReturnDate() == null
                    && (lRental.getRentalDate() != null && System.currentTimeMillis() - lRental.getRentalDate().getTime() < (long) maxRentalDays * 24 * 3600 * 1000)) {
                result.add(lRental);
            }
        }
        return result;
    }

}
