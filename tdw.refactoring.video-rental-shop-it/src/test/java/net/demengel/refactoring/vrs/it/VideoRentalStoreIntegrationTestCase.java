package net.demengel.refactoring.vrs.it;

import net.demengel.refactoring.vrs.Main;
import net.demengel.refactoring.vrs.it.driver.Application;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;

public abstract class VideoRentalStoreIntegrationTestCase extends FestSwingJUnitTestCase {

    private Application applicationDriver;

    @Override
    protected final void onSetUp() {
        TestDatabase.init();

        Main frame = GuiActionRunner.execute(new GuiQuery<Main>() {
            protected Main executeInEDT() {
                return new Main();
            }
        });
        applicationDriver = new Application(new FrameFixture(robot(), frame));
        applicationDriver.showMainWindow();
    }

    protected final Application application() {
        return applicationDriver;
    }
}
