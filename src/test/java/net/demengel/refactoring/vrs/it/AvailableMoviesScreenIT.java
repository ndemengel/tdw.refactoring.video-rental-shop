package net.demengel.refactoring.vrs.it;

import static net.demengel.refactoring.vrs.xxx.FakeMovieTable.selectAllPropertiesFromMovieTable;
import static org.fest.swing.data.TableCell.row;

import org.junit.Before;
import org.junit.Test;

public class AvailableMoviesScreenIT extends VideoRentalStoreIntegrationTestCase {

    @Before
    public void displayAvailableMoviesTab() throws Exception {
        mainWindow().tabbedPane().selectTab("Available Movies");
    }

    @Test
    public void should_display_all_movies_when_not_filtered() throws Exception {
        mainWindow().table().requireRowCount(equalToMovieCountInDatabase());
    }

    @Test
    public void should_only_display_movies_which_titles_contain_user_filter_string() throws Exception {
        // when
        enterFilterText(mainWindow().textBox("movieTitleFilter"), "he");

        // then
        mainWindow().table()
                .requireRowCount(2)
                .requireCellValue(row(0).column(0), "The Dark Knight Rises")
                .requireCellValue(row(1).column(0), "Soul Kitchen");
    }

    @Test
    public void should_only_display_one_movie_which_title_contain_user_filter_string() throws Exception {
        // when
        enterFilterText(mainWindow().textBox("movieTitleFilter"), "dark knig");

        // then
        mainWindow().table()
                .requireRowCount(1)
                .requireCellValue(row(0).column(0), "The Dark Knight Rises");
    }

    @Test
    public void should_not_display_any_movie_when_none_contain_user_filter_string() throws Exception {
        // when
        enterFilterText(mainWindow().textBox("movieTitleFilter"), "some unknown movie");

        // then
        mainWindow().table().requireRowCount(0);
    }

    private int equalToMovieCountInDatabase() {
        return selectAllPropertiesFromMovieTable().size();
    }
}
