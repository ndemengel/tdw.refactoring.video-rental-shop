package net.demengel.refactoring.vrs.it.driver;

public class DisplayedRentedMovieChecker extends TableChecker<DisplayedRentedMovieChecker> {

    private static final int DATE_COLUMN_INDEX = 3;
    private static final int CODE_COLUMN_INDEX = 0;
    private static final int CUSTOMER_ACCOUNT_NUMBER_COLUMN_INDEX = 2;
    private static final int TITLE_COLUMN_INDEX = 1;
    private static final int COLUMN_COUNT = 4;

    DisplayedRentedMovieChecker(String[][] contents) {
        super("rented movies", contents, COLUMN_COUNT);
    }

    public DisplayedRentedMovieChecker withCustomerAccountNumber(final String expectedNumber) {
        return withColumn("customer account number", CUSTOMER_ACCOUNT_NUMBER_COLUMN_INDEX, expectedNumber);
    }

    public DisplayedRentedMovieChecker withMovieCode(final String expectedCode) {
        return withColumn("movie code", CODE_COLUMN_INDEX, expectedCode);
    }

    public DisplayedRentedMovieChecker withMovieTitle(final String expectedTitle) {
        return withColumn("movie title", TITLE_COLUMN_INDEX, expectedTitle);
    }

    public DisplayedRentedMovieChecker withRentalDate(final String expectedDate) {
        return withColumn("rental date", DATE_COLUMN_INDEX, expectedDate);
    }
}
