package net.demengel.refactoring.vrs.it.driver;

import static org.fest.swing.data.TableCell.row;

import java.awt.event.KeyEvent;

import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTableFixture;

public class RentMoviesScreen {

    private static final int PRICE_COLUMN_INDEX = 2;

    private FrameFixture mainWindow;

    public RentMoviesScreen(FrameFixture mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void userScansMovieCode(String movieCode) {
        mainWindow.textBox("movieCodeToRent").setText(movieCode).pressAndReleaseKeys(KeyEvent.VK_ENTER);
    }

    public void displaysTotalPrice(String expectedPrice) {
        JTableFixture rentMoviesTable = mainWindow.dialog("rentMovies").table();
        int lastRowIndex = rentMoviesTable.rowCount() - 1;
        rentMoviesTable.requireCellValue(row(lastRowIndex).column(PRICE_COLUMN_INDEX), expectedPrice);
    }

    public void doesNotDisplayTotalPrice() {
        mainWindow.dialog("rentMovies").table().requireRowCount(0);
    }

    public void userValidatesCart() {
        mainWindow.button("validateRentalsButton").click();
    }
}
