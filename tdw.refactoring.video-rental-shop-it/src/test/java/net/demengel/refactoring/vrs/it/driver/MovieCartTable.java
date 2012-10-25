package net.demengel.refactoring.vrs.it.driver;

import static org.fest.swing.data.TableCell.row;

import org.fest.swing.fixture.JTableFixture;

public abstract class MovieCartTable {

    private final JTableFixture movieTable;

    public MovieCartTable(JTableFixture table) {
        this.movieTable = table;
    }

    protected abstract int getTotalPriceColumnIndex();

    public void displaysTotalPrice(String expectedPrice) {
        int lastRowIndex = movieTable.rowCount() - 1;
        movieTable.requireCellValue(row(lastRowIndex).column(getTotalPriceColumnIndex()), expectedPrice);
    }

    public void doesNotDisplayTotalPrice() {
        movieTable.requireRowCount(0);
    }
}
