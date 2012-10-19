package net.demengel.refactoring.vrs.xxx;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * Fake Referential table in a relational database. Static methods of this class represents SQL queries made to the database.
 */
public class FakeReferentialTable {

    private static final Map<String, String> DATA = ImmutableMap.of(
            "MAX_RENTAL_DAYS", "3", //
            "PRICE_FOR_MOVIES_NO_OLDER_THAN_3_MONTHS", "5", //
            "PRICE_FOR_MOVIES_NO_OLDER_THAN_1_YEAR", "4", //
            "PRICE_FOR_MOVIES_OLDER_THAN_1_YEAR", "2.5"
            );

    public static String selectValueFromReferentialTableWherePropertyIsEqualTo(String property) {
        return DATA.get(property);
    }
}
