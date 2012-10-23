package net.demengel.refactoring.vrs.it.driver;

import static net.demengel.refactoring.vrs.it.driver.UiUtils.enterFilterText;

import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTableFixture;

public class AvailableMoviesScreen {

    private FrameFixture mainWindow;

    public AvailableMoviesScreen(FrameFixture mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void userEntersFilterText(String text) {
        enterFilterText(mainWindow.textBox("movieTitleFilter"), text);
    }

    public void displaysMovies(String... expectedMovieTitles) {
        JTableFixture table = mainWindow.table();
        table.requireRowCount(expectedMovieTitles.length);

        for (String movieTitle : expectedMovieTitles) {
            table.cell(movieTitle);
        }
    }

    public DisplayedMovieChecker displaysMovie() {
        return new DisplayedMovieChecker(mainWindow.table().contents());
    }

    public void displaysNoMovies() {
        mainWindow.table().requireRowCount(0);
    }
}
