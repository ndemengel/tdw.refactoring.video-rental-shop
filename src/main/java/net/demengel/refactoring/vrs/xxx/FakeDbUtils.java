package net.demengel.refactoring.vrs.xxx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FakeDbUtils {

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
