package net.demengel.refactoring.vrs.dao;

import static net.demengel.refactoring.vrs.xxx.FakeMovieTable.selectAllPropertiesFromMovieTable;
import static net.demengel.refactoring.vrs.xxx.FakeMovieTable.selectAllPropertiesFromMovieTableWhereCodeIn;
import static net.demengel.refactoring.vrs.xxx.FakeMovieTable.selectAllPropertiesFromMovieTableWhereCodeIsEqualTo;
import static net.demengel.refactoring.vrs.xxx.FakeMovieTable.selectAllPropertiesFromMovieTableWhereTitleContains;

import java.util.List;

import net.demengel.refactoring.vrs.bean.Movie;

/**
 * Responsible for retrieving movie-related data
 * 
 * 
 * @author nico 17 oct. 2012
 */
public class MovieDao {

    /** The instance */
    private static MovieDao s_instance;

    /**
     * Default constructor.
     */
    private MovieDao() {

        // never used!

    }

    /**
     * Returns the instance of MovieDao
     * 
     * @return
     */
    public static MovieDao getInstance() {
        MovieDao dao = null;
        if (s_instance != null) {
            dao = s_instance;
        }
        else
        {
            dao = new MovieDao();
            s_instance = dao;
        }
        return dao;

    }

    public List listAllMovies() {
        return selectAllPropertiesFromMovieTable();
    }

    public List findMoviesByTitleLike(String p_title) {
        return selectAllPropertiesFromMovieTableWhereTitleContains(p_title);
    }

    public Movie getMovieByCode(String pMovieCode) {
        return selectAllPropertiesFromMovieTableWhereCodeIsEqualTo(pMovieCode);
    }

    public List getMoviesByCodes(List<String> pMovieCodes) {
        return selectAllPropertiesFromMovieTableWhereCodeIn(pMovieCodes);
    }
}
