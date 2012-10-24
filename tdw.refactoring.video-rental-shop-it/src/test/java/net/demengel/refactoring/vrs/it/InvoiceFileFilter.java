package net.demengel.refactoring.vrs.it;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class InvoiceFileFilter implements FilenameFilter {

    private static final Pattern INVOICE_FILE_PATTERN = Pattern.compile("Invoice-.*\\.md");

    @Override
    public boolean accept(File file, String name) {
        return INVOICE_FILE_PATTERN.matcher(name).matches();
    }
}
