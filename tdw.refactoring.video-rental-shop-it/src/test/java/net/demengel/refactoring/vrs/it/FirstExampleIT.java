package net.demengel.refactoring.vrs.it;

import static net.demengel.refactoring.vrs.xxx.FakeMovieTable.selectAllPropertiesFromMovieTable;
import static org.fest.swing.data.TableCell.row;
import net.demengel.refactoring.vrs.Main;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("Example of tests that could be improved to be less tied to the actual UI")
public class FirstExampleIT extends FestSwingJUnitTestCase {

    private FrameFixture mainWindow;

    @Override
    protected final void onSetUp() {
        Main frame = GuiActionRunner.execute(new GuiQuery<Main>() {
            protected Main executeInEDT() {
                return new Main();
            }
        });
        mainWindow = new FrameFixture(robot(), frame);
        mainWindow.show();

        mainWindow().tabbedPane().selectTab("Available Movies");
    }

    protected final FrameFixture mainWindow() {
        return mainWindow;
    }

    @Test
    public void should_display_all_movies_when_not_filtered() throws Exception {
        mainWindow().table().requireRowCount(equalToMovieCountInDatabase());
    }

    @Test
    public void should_only_display_movies_which_titles_contain_user_filter_string() throws Exception {
        // when
        mainWindow().textBox("movieTitleFilter").enterText("he");

        // then
        mainWindow().table().requireRowCount(2).requireCellValue(row(0).column(0), "The Dark Knight Rises").requireCellValue(row(1).column(0), "Soul Kitchen");
    }

    @Test
    public void should_only_display_one_movie_which_title_contain_user_filter_string() throws Exception {
        // when
        mainWindow().textBox("movieTitleFilter").enterText("dark knig");

        // then
        mainWindow().table().requireRowCount(1).requireCellValue(row(0).column(0), "The Dark Knight Rises");
    }

    @Test
    public void should_not_display_any_movie_when_none_contain_user_filter_string() throws Exception {
        // when
        mainWindow().textBox("movieTitleFilter").enterText("some unknown movie");

        // then
        mainWindow().table().requireRowCount(0);
    }

    private int equalToMovieCountInDatabase() {
        return selectAllPropertiesFromMovieTable().size();
    }
}
