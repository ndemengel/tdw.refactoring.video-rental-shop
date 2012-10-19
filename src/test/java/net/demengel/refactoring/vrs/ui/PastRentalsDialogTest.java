package net.demengel.refactoring.vrs.ui;

import org.junit.Test;

public class PastRentalsDialogTest {

    @Test
    public void testNullCustomer() throws Exception {
        try {
            new PastRentalsDialog(null);
        } catch (Exception lException) {
            // OK
        }
    }
}
