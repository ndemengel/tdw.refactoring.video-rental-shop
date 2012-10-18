package net.demengel.refactoring.vrs.util;

import java.util.Date;

import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.dao.ReferentialDao;

public class PriceUtils {

    public static double getRental(Movie p_movie, Date p_date) {
        Double price=p_movie.getForcedPrice();
        // if price is not forced
        if (price == null) {
            // if movie has been rent for less than 3 months
            if (p_date.getTime() - p_movie.getRentingStart().getTime() < 90L * 24 * 3600 * 1000) {
                price = new Double(ReferentialDao.getInstance().get(ReferentialProperties.PRICE_FOR_MOVIES_NO_OLDER_THAN_3_MONTHS));
            }
            // else, if movie has been rent for less than 1 year
            else if (p_date.getTime() - p_movie.getRentingStart().getTime() < 365L * 24 * 3600 * 1000) {
                price = Double.parseDouble(ReferentialDao.getInstance().get(ReferentialProperties.PRICE_FOR_MOVIES_NO_OLDER_THAN_1_YEAR));
            }
            // else, if movie has been rent for more than 1 year
            else {
            price = new Double(ReferentialDao.getInstance().get(ReferentialProperties.PRICE_FOR_MOVIES_OLDER_THAN_1_YEAR));
            }
        }

        return price;
    }
}
