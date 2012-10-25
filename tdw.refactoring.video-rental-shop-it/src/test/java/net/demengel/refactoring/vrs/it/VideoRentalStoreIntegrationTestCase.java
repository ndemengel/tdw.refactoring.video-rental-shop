package net.demengel.refactoring.vrs.it;

import java.io.File;

import net.demengel.refactoring.vrs.Main;
import net.demengel.refactoring.vrs.it.driver.Application;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.junit.testcase.FestSwingJUnitTestCase;
import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TestName;

public abstract class VideoRentalStoreIntegrationTestCase extends FestSwingJUnitTestCase {

    @Rule
    public TestName testName = new TestName();

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

    @After
    public void deleteInvoiceFiles() {
        for (File f : new File(".").listFiles(new InvoiceFileFilter())) {
            f.delete();
        }
    }

    protected final Application application() {
        return applicationDriver;
    }
}
