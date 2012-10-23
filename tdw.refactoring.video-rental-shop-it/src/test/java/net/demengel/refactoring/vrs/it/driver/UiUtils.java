package net.demengel.refactoring.vrs.it.driver;

import org.fest.swing.fixture.JTextComponentFixture;

public class UiUtils {

    /**
     * Speeds up text entering by only calling {@link JTextComponentFixture#enterText(String) enterText} for the last character of the given
     * filter text (the beginning of the text being set using {@link JTextComponentFixture#setText(String) setText}).
     * <p>
     * To be used cautiously as it may bias the test.
     * </p>
     */
    public static JTextComponentFixture enterFilterText(JTextComponentFixture textCompFixture, String filterText) {
        if (filterText.length() <= 1) {
            return textCompFixture.deleteText().enterText(filterText);
        }

        int indexOfLastCharacter = filterText.length() - 1;
        String textStart = filterText.substring(0, indexOfLastCharacter);
        String lastCharacter = filterText.substring(indexOfLastCharacter);

        return textCompFixture.setText(textStart).enterText(lastCharacter);
    }
}
