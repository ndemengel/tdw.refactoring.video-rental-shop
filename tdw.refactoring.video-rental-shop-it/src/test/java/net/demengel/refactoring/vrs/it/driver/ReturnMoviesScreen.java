package net.demengel.refactoring.vrs.it.driver;

import org.fest.swing.fixture.FrameFixture;

public class ReturnMoviesScreen {

    private final FrameFixture mainWindow;
    private final MovieCartTable returnedMoviesTable;

    public ReturnMoviesScreen(FrameFixture mainWindow) {
        this.mainWindow = mainWindow;

        returnedMoviesTable = new MovieCartTable(mainWindow.dialog("returnMovies").table()) {
            @Override
            protected int getTotalPriceColumnIndex() {
                return 3;
            }
        };
    }

    public void displaysTotalPrice(String expectedPrice) {
        returnedMoviesTable.displaysTotalPrice(expectedPrice);
    }

    public void doesNotDisplayTotalPrice() {
        returnedMoviesTable.doesNotDisplayTotalPrice();
    }

    public void userValidatesReturn() {
        mainWindow.button("validateReturnsButton").click();
    }
}
