package net.demengel.refactoring.vrs.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.bean.Renting;
import net.demengel.refactoring.vrs.dao.MovieDao;
import net.demengel.refactoring.vrs.dao.ReferentialDao;
import net.demengel.refactoring.vrs.dao.RentingDao;
import net.demengel.refactoring.vrs.util.ReferentialProperties;

public class RentingHelper implements ReferentialProperties {

    private static final Object LOCK = new Object();
    private static RentingHelper s_instance;

    private RentingDao m_rentingDAO;
    private MovieDao m_movieDAO = MovieDao.getInstance();

    // 2010/11/06 FIX: Addition of 'synchronized' to avoid instantiating the singleton twice
    public static RentingHelper getInstance() {
        if (s_instance == null) {

            synchronized (LOCK) {

                if (s_instance == null) {
                    s_instance = new RentingHelper();
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
    
    private RentingHelper()
    {
        m_rentingDAO= RentingDao.getInstance();
    }

    public List<Renting> getCurrentRentingsWithTitle() {
        List<Renting> lFindCurrentRentings = m_rentingDAO.findCurrentRentings();
        List<String> codes = new ArrayList<String>(lFindCurrentRentings.size());
        for (Renting lRenting : lFindCurrentRentings) {
            codes.add(lRenting.getMovieCode());
        }
        List<Movie> getMoviesByCodes = m_movieDAO.getMoviesByCodes(codes);
        for (Renting lRenting : lFindCurrentRentings) {
            for (Movie lMovie : getMoviesByCodes) {
                if (lMovie.getCode() .equals(lRenting.getMovieCode()))
                {
                    lRenting.setMovieTitle(lMovie.getTitle());
                }
            }
        }
        return lFindCurrentRentings;
    }
    
    // 2011/09/17 performance improvement: does not retrieve titles for rentings that will be filtered 
    public List<Renting> getLateReturnsWithTitle() {
        List<Renting> lFindCurrentRentings = m_rentingDAO.findCurrentRentings();
        List<String> codes = new ArrayList<String>(lFindCurrentRentings.size());
        Integer maxRentingDays = new Integer(ReferentialDao.getInstance().get(MAX_RENTING_DAYS));
        for (Renting lRenting : lFindCurrentRentings) {
            // only keeps codes of late returns
            if (lRenting != null
                    && lRenting.getReturnDate() == null
                    && (lRenting.getRentingDate() != null && System.currentTimeMillis() - lRenting.getRentingDate().getTime() < (long) maxRentingDays * 24 * 3600 * 1000)) {
                codes.add(lRenting.getMovieCode());
            }
        }
        // then retrieve titles
        List<Movie> getMoviesByCodes = m_movieDAO.getMoviesByCodes(codes);
        for (Renting lRenting : lFindCurrentRentings) {
            for (Movie lMovie : getMoviesByCodes) {
                if (lMovie.getCode() .equals(lRenting.getMovieCode()))
                {
                    lRenting.setMovieTitle(lMovie.getTitle());
                }
            }
        }
        return lFindCurrentRentings;
    }
    
//    public List<Renting> getLateReturnsWithTitle() {
//        List<Renting> lCurrentRentingsWithTitle = getCurrentRentingsWithTitle();
//        List<Renting> result = new ArrayList<Renting>();
//        Integer maxRentingDays = new Integer(ReferentialDao.getInstance().get(MAX_RENTING_DAYS));
//        for (Renting lRenting : lCurrentRentingsWithTitle) {
//            if (lRenting != null
//                    && lRenting.getReturnDate() == null
//                    && (lRenting.getRentingDate() != null && System.currentTimeMillis() - lRenting.getRentingDate().getTime() < (long) maxRentingDays * 24 * 3600 * 1000)) {
//                result.add(lRenting);
//            }
//        }
//        return result;
//    }
    
    public void addTitlesToRentings(List<Renting> pRentings) {
        List<String> codes = new ArrayList<String>(pRentings.size());
        for (Renting lRenting : pRentings) {
            codes.add(lRenting.getMovieCode());
        }
        
        List<Movie> getMoviesByCodes = m_movieDAO.getMoviesByCodes(codes);
        
        for (Renting lRenting : pRentings) {
            for (Movie lMovie : getMoviesByCodes) {
                if (lMovie.getCode() .equals(lRenting.getMovieCode()))
                {
                    lRenting.setMovieTitle(lMovie.getTitle());
                }
            }
        }
    }
}
