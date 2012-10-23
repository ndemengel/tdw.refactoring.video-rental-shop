package net.demengel.refactoring.vrs.xxx;

public class FakeDb {

    public static void truncateAllTables() {
        FakeCustomerTable.truncate();
        FakeMovieTable.truncate();
        FakeReferentialTable.truncate();
        FakeRentalTable.truncate();
    }

}
