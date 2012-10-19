package net.demengel.refactoring.vrs.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PrintUtils {

    // This application does not directly print documents. Instead, documents to print are to be placed in a specific directory that is
    // monitored by an external print process. Supported document types are: plain text, Markdown, HTML
    private static final File DIRECTORY_FOR_DOCUMENTS_TO_PRINT = new File(".");

    public static void print(String pDocumentName, String pDocumentContent) {
        File documentFile = new File(DIRECTORY_FOR_DOCUMENTS_TO_PRINT, pDocumentName);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(documentFile);
            fileWriter.write(pDocumentContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
