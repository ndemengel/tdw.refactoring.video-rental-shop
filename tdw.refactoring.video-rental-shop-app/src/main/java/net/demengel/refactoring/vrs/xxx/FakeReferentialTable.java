package net.demengel.refactoring.vrs.xxx;

import static com.google.common.collect.ImmutableMap.of;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

/**
 * Fake Referential table in a relational database. Static methods of this class represents SQL queries made to the database.
 */
public class FakeReferentialTable {

    private static final Map<String, String> DATA = newHashMap(of(
            "MAX_RENTAL_DAYS", "3", //
            "PRICE_FOR_MOVIES_NO_OLDER_THAN_3_MONTHS", "5", //
            "PRICE_FOR_MOVIES_NO_OLDER_THAN_1_YEAR", "4", //
            "PRICE_FOR_MOVIES_OLDER_THAN_1_YEAR", "2.5"
            ));

    public static String selectValueFromReferentialTableWherePropertyIsEqualTo(String property) {
        return DATA.get(property);
    }

    public static String updateReferentialTableSetValueWherePropertyIsEqualTo(String value, String property) {
        return DATA.put(property, value);
    }

    public static String insertPropertyAndValueIntoReferentialTable(String property, String value) {
        return DATA.put(property, value);
    }

    public static void truncate() {
        DATA.clear();
    }
}
