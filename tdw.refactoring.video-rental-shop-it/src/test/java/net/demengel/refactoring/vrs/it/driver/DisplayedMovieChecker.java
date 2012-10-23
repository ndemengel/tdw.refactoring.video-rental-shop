package net.demengel.refactoring.vrs.it.driver;

public class DisplayedMovieChecker extends TableChecker<DisplayedMovieChecker> {

    private static final int PRICE_COLUMN_INDEX = 3;
    private static final int QUANTITY_COLUMN_INDEX = 2;
    private static final int TITLE_COLUMN_INDEX = 0;
    private static final int COLUMN_COUNT = 7;

    DisplayedMovieChecker(String[][] contents) {
        super("movies", contents, COLUMN_COUNT);
    }

    public DisplayedMovieChecker withAvailableQuantity(final int expectedQuantity) {
        return withColumn("available quantity", QUANTITY_COLUMN_INDEX, String.valueOf(expectedQuantity));
    }

    public DisplayedMovieChecker withPrice(final String expectedPrice) {
        return withColumn("price", PRICE_COLUMN_INDEX, expectedPrice);
    }

    public DisplayedMovieChecker withTitle(final String expectedTitle) {
        return withColumn("title", TITLE_COLUMN_INDEX, expectedTitle);
    }
}
