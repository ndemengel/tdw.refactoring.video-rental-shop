package net.demengel.refactoring.vrs.manager;

import java.util.List;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.bean.Movie;

/**
 * @author nico 18 oct. 2012
 */
public class RentingManager {

    private static RentingManager s_instance;

    public static RentingManager getInstance() {
        if (s_instance == null) {
            s_instance = new RentingManager();
        }

        return s_instance;
    }

    private RentingManager() {
        // hidden: this class is a singleton!
    }

    public void printProvisionalInvoice(Customer pSelectedCustomer, List<Movie> pMovies) {
        // TODO provisional invoice
        // TODO tx
    }
}
