package net.demengel.refactoring.vrs.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Renting;
import net.demengel.refactoring.vrs.dao.ReferentialDao;
import net.demengel.refactoring.vrs.dao.RentingDao;
import net.demengel.refactoring.vrs.util.LRUCache;
import net.demengel.refactoring.vrs.util.ReferentialProperties;

public class CustomerHelper implements ReferentialProperties {

    private static final Object LOCK = new Object();
    private static CustomerHelper s_instance;

    private LRUCache<String, List<Renting>> rentingsByCustomerId = new LRUCache<String, List<Renting>>(4000); // TODO 2011/02/13 retrieve
                                                                                                              // cache size from system
                                                                                                              // property
    private RentingDao m_rentingDAO;

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
        m_rentingDAO= RentingDao.getInstance();
    }

    /**
     * Get current rentings for customer.
     * 
     * @param pAccountNumber the account number of the customer
     * @return the current retnings
     */
    public List<Renting> getCurrentRentingsForCustomer(String pAccountNumber) {
        // 2011/02/13 cache added to improve performance
        List<Renting> lsit;
        if (rentingsByCustomerId.containsKey(pAccountNumber)) {
            lsit = rentingsByCustomerId.get(pAccountNumber);
            
        }
        else {
            lsit = m_rentingDAO.findRentingsForCustomer(pAccountNumber);
        }
        
        List<Renting> lcurRentings = new ArrayList<Renting>(lsit);
        Iterator<Renting> iterator = lcurRentings.iterator();
        while (iterator.hasNext()) {
            Renting r = iterator.next();
            if (r.getReturnDate() != null) {
                iterator.remove();
            }
        }
        return lcurRentings;
    }
    
    /**
     * Get late returns for customer.
     * 
     * @param pAccountNumber the account number of the customer
     * @return the current retnings
     */
    public List<Renting> getLateReturnsForCustomer(String pAccountNumber) {
        // 2011/02/13 cache added to improve performance
        List<Renting> lsit;
        if (rentingsByCustomerId.containsKey(pAccountNumber)) {
            lsit = rentingsByCustomerId.get(pAccountNumber);
            
        }
        else {
            lsit = m_rentingDAO.findRentingsForCustomer(pAccountNumber);
        }
        List<Renting> result = new ArrayList<Renting>();
        Integer maxRentingDays = new Integer(ReferentialDao.getInstance().get(MAX_RENTING_DAYS));
        for (Renting lRenting : lsit) {
            if (lRenting != null
                    && lRenting.getReturnDate() == null
                    && (lRenting.getRentingDate() != null && System.currentTimeMillis() - lRenting.getRentingDate().getTime() < (long) maxRentingDays * 24 * 3600 * 1000)) {
                result.add(lRenting);
            }
        }
        return result;
    }

}
