package net.demengel.refactoring.vrs.util;

import java.io.File;
import java.util.Scanner;

import org.junit.Test;

public class PrintUtilsTest {

    @Test
    public void testPrint() throws Exception {
        PrintUtils.print("myDocument", "blah");

        Scanner lScanner = new Scanner(new File("myDocument"));
        while (lScanner.hasNextLine()) {
            System.out.println(lScanner.nextLine());
        }
    }
}
