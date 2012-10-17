package net.demengel.refactoring.vrs.dao;

import net.demengel.refactoring.vrs.xxx.FakeReferentialDb;

/**
 * The DAO for accessing referential data.
 * 
 * @author nico 17 oct. 2012
 */
public class ReferentialDao {

    /** The instance */
    private static ReferentialDao s_instance;

    /**
     * Default constructor.
     */
    private ReferentialDao() {

        // never used!

    }

    /**
     * Returns the instance of MovieDao
     * 
     * @return
     */
    public static ReferentialDao getInstance() {
        ReferentialDao dao = null;
        if (s_instance != null) {
            dao = s_instance;
        }
        else
        {
            dao = new ReferentialDao();
            s_instance = dao;
        }
        return dao;

    }

    public String get(String property) {
        return FakeReferentialDb.get(property);
    }
}
