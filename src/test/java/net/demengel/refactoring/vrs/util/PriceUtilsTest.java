package net.demengel.refactoring.vrs.util;

import static org.fest.assertions.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import net.demengel.refactoring.vrs.bean.Movie;

import org.junit.Test;


public class PriceUtilsTest {

    @Test
    public void testGetRentalPrice() throws Exception {
        Movie lMovie = new Movie();
        Calendar lCalendar = Calendar.getInstance();
        lCalendar.clear();
        lCalendar.set(2012, 10, 11);
        lMovie.setRentalStart(lCalendar.getTime());
        lCalendar.clear();
        lCalendar.set(2012, 10, 19);
        Date lDate = lCalendar.getTime();
        double price = PriceUtils.getRentalPrice(lMovie, lDate);
        assertEquals(price, price);
    }
}
