package net.demengel.refactoring.vrs.util;

/**
 * Holds names for various referential properties.
 * 
 * @author nico 17 oct. 2012
 */
public interface ReferentialProperties {

    /**
     * The maximum of days a customer is allowed to rent a movie before we shout at her.
     */
    public static final String MAX_RENTAL_DAYS = "MAX_RENTAL_DAYS";
    /**
     * The price for movies no older than 3 months.
     */
    public static final String PRICE_FOR_MOVIES_NO_OLDER_THAN_3_MONTHS = "PRICE_FOR_MOVIES_NO_OLDER_THAN_3_MONTHS";
    /**
     * The price for movies no older than 1 yer.
     */
    public static final String PRICE_FOR_MOVIES_NO_OLDER_THAN_1_YEAR = "PRICE_FOR_MOVIES_NO_OLDER_THAN_1_YEAR";
    /**
     * The price for movies older than 1 month.
     */
    public static final String PRICE_FOR_MOVIES_OLDER_THAN_1_YEAR = "PRICE_FOR_MOVIES_OLDER_THAN_1_YEAR";

}
