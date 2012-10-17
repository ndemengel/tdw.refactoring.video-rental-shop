package net.demengel.refactoring.vrs.dao;

import java.util.List;

import net.demengel.refactoring.vrs.xxx.FakeMovieDb;

/**
 * Responsible for retrieving movie-related data
 * 
 * 
 * @author nico
 * 17 oct. 2012
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
        return FakeMovieDb.getAllMovies();
    }
}
