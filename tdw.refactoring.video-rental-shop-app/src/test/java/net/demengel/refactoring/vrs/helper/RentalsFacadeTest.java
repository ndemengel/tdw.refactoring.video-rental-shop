package net.demengel.refactoring.vrs.helper;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

public class RentalsFacadeTest {

    @Test
    public void testPrint() throws Exception {
        RentalsFacade.print("myDocument", "blah");

        Scanner lScanner = new Scanner(new File("myDocument"));
        while (lScanner.hasNextLine()) {
            System.out.println(lScanner.nextLine());
        }
    }
}
