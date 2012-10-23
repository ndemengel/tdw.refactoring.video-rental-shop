package net.demengel.refactoring.vrs.it.driver;

public class DisplayedCustomerChecker extends TableChecker<DisplayedCustomerChecker> {

    private static final int ACCOUNT_NUMBER_COLUMN_INDEX = 0;
    private static final int LATE_RETURNS_COLUMN_INDEX = 3;
    private static final int RENTED_MOVIES_COLUMN_INDEX = 2;
    private static final int COLUMN_COUNT = 4;

    DisplayedCustomerChecker(String[][] tableContents) {
        super("customers", tableContents, COLUMN_COUNT);
    }

    public DisplayedCustomerChecker withAccountNumber(String expectedAccountNumber) {
        return withColumn("account number", ACCOUNT_NUMBER_COLUMN_INDEX, expectedAccountNumber);
    }

    public DisplayedCustomerChecker withLateReturns(int expectedLateReturnsCount) {
        return withColumn("late returns", LATE_RETURNS_COLUMN_INDEX, String.valueOf(expectedLateReturnsCount));
    }

    public DisplayedCustomerChecker withRentedMovies(int expectedRentedMoviesCount) {
        return withColumn("rented movies", RENTED_MOVIES_COLUMN_INDEX, String.valueOf(expectedRentedMoviesCount));
    }
}
