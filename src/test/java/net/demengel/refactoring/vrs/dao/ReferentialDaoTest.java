package net.demengel.refactoring.vrs.dao;

import static org.junit.Assert.fail;

import org.junit.Test;

public class ReferentialDaoTest {

    @Test
    public void testGetUnknownProperty() {
        try {
            ReferentialDao.getInstance().get("blah");
        } catch (Exception lE) {
            fail();
        }
    }

    @Test
    public void testGetNullProperty() {
        // TODO
    }
}
