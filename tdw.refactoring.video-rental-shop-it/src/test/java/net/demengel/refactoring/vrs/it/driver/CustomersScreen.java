package net.demengel.refactoring.vrs.it.driver;

import static net.demengel.refactoring.vrs.it.driver.UiUtils.enterFilterText;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.data.TableCell.row;

import org.fest.swing.data.TableCell;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JPopupMenuFixture;
import org.fest.swing.fixture.JTableFixture;

public class CustomersScreen {

    private final FrameFixture mainWindow;

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

    public void selectCustomer(String customerAccountNumber) {
        mainWindow.table().selectRows(findRowIndexForCustomer(customerAccountNumber));
    }

    private int findRowIndexForCustomer(String customerAccountNumber) {
        return mainWindow.table().cell(customerAccountNumber).row;
    }

    public void proposesActions(String... expectedActions) {
        assertThat(showPopupMenuForSelectedRow().menuLabels()).hasSize(expectedActions.length).contains((Object[]) expectedActions);
    }

    private JPopupMenuFixture showPopupMenuForSelectedRow() {
        TableCell firstCellOfSelectedRow = row(mainWindow.table().target.getSelectedRow()).column(0);
        return mainWindow.table().showPopupMenuAt(firstCellOfSelectedRow);
    }

    public RentMoviesScreen showRentMoviesScreenForCustomer(String customerAccountNumber) {
        selectCustomer(customerAccountNumber);
        showPopupMenuForSelectedRow().menuItemWithPath("Rent Movies...").click();
        return new RentMoviesScreen(mainWindow);
    }

    public ReturnMoviesScreen showReturnMoviesScreenForCustomer(String customerAccountNumber) {
        selectCustomer(customerAccountNumber);
        showPopupMenuForSelectedRow().menuItemWithPath("Return Movies...").click();
        return new ReturnMoviesScreen(mainWindow);
    }
}
