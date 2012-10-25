package net.demengel.refactoring.vrs.it.driver;

import java.awt.event.KeyEvent;

import org.fest.swing.fixture.FrameFixture;

public class RentMoviesScreen {

    private final FrameFixture mainWindow;
    private final MovieCartTable rentedMoviesTable;

    public RentMoviesScreen(FrameFixture mainWindow) {
        this.mainWindow = mainWindow;

        rentedMoviesTable = new MovieCartTable(mainWindow.dialog("rentMovies").table()) {
            @Override
            protected int getTotalPriceColumnIndex() {
                return 2;
            }
        };
    }

    public void userScansMovieCode(String movieCode) {
        mainWindow.textBox("movieCodeToRent").setText(movieCode).pressAndReleaseKeys(KeyEvent.VK_ENTER);
    }

    public void userValidatesCart() {
        mainWindow.button("validateRentalsButton").click();
    }

    public void displaysTotalPrice(String expectedPrice) {
        rentedMoviesTable.displaysTotalPrice(expectedPrice);
    }

    public void doesNotDisplayTotalPrice() {
        rentedMoviesTable.doesNotDisplayTotalPrice();
    }
}
