package net.demengel.refactoring.vrs.it.driver;

import org.fest.swing.fixture.FrameFixture;

public class Application {

    private FrameFixture mainWindow;

    public Application(FrameFixture mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void showMainWindow() {
        mainWindow.show();
    }

    public AvailableMoviesScreen showAvailableMoviesScreen() {
        mainWindow.tabbedPane().selectTab("Available Movies");
        return new AvailableMoviesScreen(mainWindow);
    }

    public CustomersScreen showCustomersScreen() {
        mainWindow.tabbedPane().selectTab("Customers");
        return new CustomersScreen(mainWindow);
    }
}
