package net.demengel.refactoring.vrs.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.bean.Rental;
import net.demengel.refactoring.vrs.dao.MovieDao;
import net.demengel.refactoring.vrs.dao.ReferentialDao;
import net.demengel.refactoring.vrs.dao.RentalDao;
import net.demengel.refactoring.vrs.util.ReferentialProperties;

public class RentalHelper implements ReferentialProperties {

    private static final Object LOCK = new Object();
    private static RentalHelper s_instance;

    private RentalDao m_rentalDAO;
    private MovieDao m_movieDAO = MovieDao.getInstance();

    // 2010/11/06 FIX: Addition of 'synchronized' to avoid instantiating the singleton twice
    public static RentalHelper getInstance() {
        if (s_instance == null) {

            synchronized (LOCK) {

                if (s_instance == null) {
                    s_instance = new RentalHelper();
                }
            }

        }

        return s_instance;
    }
    
//    public static MovieHelper getInstance() {
//        if (s_instance == null) {
//            s_instance = new MovieHelper();
//        }
//        return s_instance;
//    }
    
    private RentalHelper()
    {
        m_rentalDAO= RentalDao.getInstance();
    }

    public List<Rental> getCurrentRentalsWithTitle() {
        List<Rental> lFindCurrentRentals = m_rentalDAO.findCurrentRentals();
        List<String> codes = new ArrayList<String>(lFindCurrentRentals.size());
        for (Rental lRental : lFindCurrentRentals) {
            codes.add(lRental.getMovieCode());
        }
        List<Movie> getMoviesByCodes = m_movieDAO.getMoviesByCodes(codes);
        for (Rental lRental : lFindCurrentRentals) {
            for (Movie lMovie : getMoviesByCodes) {
                if (lMovie.getCode() .equals(lRental.getMovieCode()))
                {
                    lRental.setMovieTitle(lMovie.getTitle());
                }
            }
        }
        return lFindCurrentRentals;
    }
    
    // 2011/09/17 performance improvement: does not retrieve titles for rentals that will be filtered 
    public List<Rental> getLateReturnsWithTitle() {
        List<Rental> lFindCurrentRentals = m_rentalDAO.findCurrentRentals();
        List<String> codes = new ArrayList<String>(lFindCurrentRentals.size());
        Integer maxRentalDays = new Integer(ReferentialDao.getInstance().get(MAX_RENTAL_DAYS));
        for (Rental lRental : lFindCurrentRentals) {
            // only keeps codes of late returns
            if (lRental != null
                    && lRental.getReturnDate() == null
                    && (lRental.getRentalDate() != null && System.currentTimeMillis() - lRental.getRentalDate().getTime() < (long) maxRentalDays * 24 * 3600 * 1000)) {
                codes.add(lRental.getMovieCode());
            }
        }
        // then retrieve titles
        List<Movie> getMoviesByCodes = m_movieDAO.getMoviesByCodes(codes);
        for (Rental lRental : lFindCurrentRentals) {
            for (Movie lMovie : getMoviesByCodes) {
                if (lMovie.getCode() .equals(lRental.getMovieCode()))
                {
                    lRental.setMovieTitle(lMovie.getTitle());
                }
            }
        }
        return lFindCurrentRentals;
    }
    
//    public List<Rental> getLateReturnsWithTitle() {
//        List<Rental> lCurrentRentalsWithTitle = getCurrentRentalsWithTitle();
//        List<Rental> result = new ArrayList<Rental>();
//        Integer maxRentalDays = new Integer(ReferentialDao.getInstance().get(MAX_RENTAL_DAYS));
//        for (Rental lRental : lCurrentRentalsWithTitle) {
//            if (lRental != null
//                    && lRental.getReturnDate() == null
//                    && (lRental.getRentalDate() != null && System.currentTimeMillis() - lRental.getRentalDate().getTime() < (long) maxRentalDays * 24 * 3600 * 1000)) {
//                result.add(lRental);
//            }
//        }
//        return result;
//    }
    
    public void addTitlesToRentals(List<Rental> pRentals) {
        List<String> codes = new ArrayList<String>(pRentals.size());
        for (Rental lRental : pRentals) {
            codes.add(lRental.getMovieCode());
        }
        
        List<Movie> getMoviesByCodes = m_movieDAO.getMoviesByCodes(codes);
        
        for (Rental lRental : pRentals) {
            for (Movie lMovie : getMoviesByCodes) {
                if (lMovie.getCode() .equals(lRental.getMovieCode()))
                {
                    lRental.setMovieTitle(lMovie.getTitle());
                }
            }
        }
    }
}
