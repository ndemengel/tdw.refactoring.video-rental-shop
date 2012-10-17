package net.demengel.refactoring.vrs.xxx;

import java.util.HashMap;
import java.util.Map;

public class FakeReferentialDb {

    private static final Map<String, String> DATA = new HashMap<String, String>();
    static {
        DATA.put("PRICE_FOR_MOVIES_NO_OLDER_THAN_3_MONTHS", "5");
        DATA.put("PRICE_FOR_MOVIES_NO_OLDER_THAN_1_YEAR", "4");
        DATA.put("PRICE_FOR_MOVIES_OLDER_THAN_1_YEAR", "2.5");
    }

    public static String get(String property) {
        return DATA.get(property);
    }
}
