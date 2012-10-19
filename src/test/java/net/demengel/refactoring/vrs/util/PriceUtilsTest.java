package net.demengel.refactoring.vrs.util;

import static net.demengel.refactoring.vrs.xxx.FakeReferentialTable.updateReferentialTableSetValueWherePropertyIsEqualTo;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import net.demengel.refactoring.vrs.bean.Movie;

import org.junit.Test;

public class PriceUtilsTest {

    @Test
    public void testGetRentalPrice() throws Exception {
        updateReferentialTableSetValueWherePropertyIsEqualTo("42", "PRICE_FOR_MOVIES_NO_OLDER_THAN_3_MONTHS");
        updateReferentialTableSetValueWherePropertyIsEqualTo("27", "PRICE_FOR_MOVIES_NO_OLDER_THAN_1_YEAR");
        updateReferentialTableSetValueWherePropertyIsEqualTo("13", "PRICE_FOR_MOVIES_OLDER_THAN_1_YEAR");
        Calendar lCalendar = Calendar.getInstance();
        lCalendar.clear();
        lCalendar.set(2012, 10, 19);
        Date lDate = lCalendar.getTime();
        Movie lMovie1 = new Movie();
        lCalendar.clear();
        lCalendar.set(2012, 10, 11);
        lMovie1.setRentalStart(lCalendar.getTime());
        Movie lMovie2 = new Movie();
        lCalendar.clear();
        lCalendar.set(2011, 12, 6);
        lMovie2.setRentalStart(lCalendar.getTime());
        Movie lMovie3 = new Movie();
        lCalendar.clear();
        lCalendar.set(2009, 5, 23);
        lMovie3.setRentalStart(lCalendar.getTime());

        double price = PriceUtils.getRentalPrice(lMovie1, lDate);
        assertEquals(42, price, 0);
        price = PriceUtils.getRentalPrice(lMovie2, lDate);
        assertEquals(27, price, 0);
        price = PriceUtils.getRentalPrice(lMovie3, lDate);
        assertEquals(13, price, 0);
    }
}
