package net.demengel.refactoring.vrs.xxx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.demengel.refactoring.vrs.dao.Transaction;

public class FakeDbUtils {

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void doInTransaction(Transaction transaction, Runnable query) {
        ((FakeTransaction) transaction).addQuery(query);
    }
}
