package net.demengel.refactoring.vrs.it;

import net.demengel.refactoring.vrs.Main;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;

public abstract class VideoRentalStoreIntegrationTestCase extends FestSwingJUnitTestCase {

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
    }

    protected final FrameFixture mainWindow() {
        return mainWindow;
    }

    /**
     * Speeds up text entering by only calling {@link JTextComponentFixture#enterText(String) enterText} for the last character of the given
     * filter text (the beginning of the text being set using {@link JTextComponentFixture#setText(String) setText}).
     * <p>
     * To be used cautiously as it may bias the test.
     * </p>
     */
    protected final JTextComponentFixture enterFilterText(JTextComponentFixture textCompFixture, String filterText) {
        if (filterText.length() <= 1) {
            return textCompFixture.enterText(filterText);
        }

        int indexOfLastCharacter = filterText.length() - 1;
        String textStart = filterText.substring(0, indexOfLastCharacter);
        String lastCharacter = filterText.substring(indexOfLastCharacter);

        return textCompFixture.setText(textStart).enterText(lastCharacter);
    }
}
