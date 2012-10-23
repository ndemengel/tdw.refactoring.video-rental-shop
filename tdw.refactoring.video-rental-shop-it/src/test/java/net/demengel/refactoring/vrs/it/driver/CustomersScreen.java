package net.demengel.refactoring.vrs.it.driver;

import static net.demengel.refactoring.vrs.it.driver.UiUtils.enterFilterText;

import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTableFixture;

public class CustomersScreen {

    private FrameFixture mainWindow;

    public CustomersScreen(FrameFixture mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void userEntersNumberFilterText(String text) {
        enterFilterText(mainWindow.textBox("accountNumberFilter"), text);
    }

    public void userEntersNameFilterText(String text) {
        enterFilterText(mainWindow.textBox("customerNameFilter"), text);
    }

    public void displaysCustomers(String... expectedCustomerNames) {
        JTableFixture table = mainWindow.table();
        table.requireRowCount(expectedCustomerNames.length);

        for (String customerName : expectedCustomerNames) {
            table.cell(customerName);
        }
    }

    public DisplayedCustomerChecker displaysCustomer() {
        return new DisplayedCustomerChecker(mainWindow.table().contents());
    }

    public void displaysNoCustomers() {
        mainWindow.table().requireRowCount(0);
    }
}
