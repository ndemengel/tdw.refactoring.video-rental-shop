package net.demengel.refactoring.vrs.it.driver;

import static net.demengel.refactoring.vrs.it.driver.UiUtils.enterFilterText;
import static org.fest.assertions.Assertions.assertThat;

import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTableFixture;
import org.fest.swing.fixture.JTextComponentFixture;

public class RentedMoviesScreen {

    private final FrameFixture mainWindow;

    public RentedMoviesScreen(FrameFixture mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void userEntersFilterText(String text) {
        enterFilterText(rentalTitleFilterBox(), text);
    }

    private JTextComponentFixture rentalTitleFilterBox() {
        return mainWindow.textBox("rentalTitleFilter");
    }

    public void displaysMovies(String... expectedMovieCodes) {
        JTableFixture table = mainWindow.table();

        // same movie may appear several times
        assertThat(table.rowCount()).isGreaterThanOrEqualTo(expectedMovieCodes.length);

        for (String movieCode : expectedMovieCodes) {
            table.cell(movieCode);
        }
    }

    public DisplayedRentedMovieChecker displaysMovie() {
        return new DisplayedRentedMovieChecker(mainWindow.table().contents());
    }

    public void displaysNoMovies() {
        mainWindow.table().requireRowCount(0);
    }

    public void resetFilter() {
        rentalTitleFilterBox().enterText("x");
        rentalTitleFilterBox().deleteText();
    }
}
