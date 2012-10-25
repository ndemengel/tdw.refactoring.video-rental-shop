package net.demengel.refactoring.vrs.it.driver;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import com.google.common.base.Predicate;

public class TableChecker<TC extends TableChecker<TC>> {

    private static final String NL = System.getProperty("line.separator");

    private final String tableContentDescription;
    private String descriptionSoFar;
    private List<String[]> matchingLines;
    private boolean firstCheck = true;

    TableChecker(String tableContentDescription, String[][] tableContents, int expectedColumnCount) {
        assertThat(tableContents).as(tableContentDescription).isNotEmpty();
        assertThat(tableContents[0]).as("number of columns for " + tableContentDescription).hasSize(expectedColumnCount);

        this.tableContentDescription = tableContentDescription;
        this.descriptionSoFar = "no " + tableContentDescription + " with ";
        this.matchingLines = asList(tableContents);
    }

    public final TC withColumn(String columnName, final int columnIndex, final String expectedValue) {
        String checkDescription = columnName + " = " + expectedValue;

        if (firstCheck) {
            firstCheck = false;
        }
        else {
            descriptionSoFar += " and ";
        }

        descriptionSoFar += checkDescription;

        List<String[]> newMatchingLines = keepLinesWhereColumnHasValue(columnIndex, expectedValue);

        if (newMatchingLines.isEmpty()) {
            fail(errorDescriptionFrom(checkDescription));
        }

        matchingLines = newMatchingLines;

        @SuppressWarnings("unchecked")
        TC thisChecker = (TC) this;
        return thisChecker;
    }

    private List<String[]> keepLinesWhereColumnHasValue(final int columnIndex, final String expectedValue) {
        List<String[]> newMatchingLines = newArrayList(filter(matchingLines, new Predicate<String[]>() {
            public boolean apply(String[] row) {
                return row[columnIndex].equals(expectedValue);
            }
        }));
        return newMatchingLines;
    }

    private String errorDescriptionFrom(String checkDescription) {
        StringBuilder sb = new StringBuilder(descriptionSoFar).append(NL).append(NL);
        sb.append(tableContentDescription + " before filtering for ").append(checkDescription).append(":").append(NL);
        for (String[] line : matchingLines) {
            sb.append("        ").append(asList(line)).append(NL);
        }
        return sb.toString();
    }
}
