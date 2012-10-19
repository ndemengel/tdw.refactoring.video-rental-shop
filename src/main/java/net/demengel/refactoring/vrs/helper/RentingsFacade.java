package net.demengel.refactoring.vrs.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.demengel.refactoring.vrs.bean.Customer;
import net.demengel.refactoring.vrs.bean.Movie;
import net.demengel.refactoring.vrs.bean.Renting;
import net.demengel.refactoring.vrs.util.PriceUtils;
import net.demengel.refactoring.vrs.util.PrintUtils;

/**
 * This app really s..., I'm getting tired of this procedural stuff. Let's do it the real way: with objects! So for once, this class is
 * intended to represent a concept from end to end.
 * 
 * @author GOD 16 jan. 2012
 */
abstract class RentingsFacade {

    // new line
    private static final String NL = "\n";

    protected Customer mSelectedCustomer;
    protected List<Movie> mRentedMovies;
    protected Date mRentingDate;
    protected List<Renting> mRentings;
    protected Date mReturnDate;

    /**
     * Constructor for new rentings.
     * 
     * @param pSelectedCustomer
     * @param pMoviesToRent
     * @param pRentingDate
     */
    public RentingsFacade(Customer pSelectedCustomer, List<Movie> pMoviesToRent, Date pRentingDate) {
        mSelectedCustomer = pSelectedCustomer;
        mRentedMovies = pMoviesToRent;
        mRentingDate = pRentingDate;
    }

    /**
     * Constructor for existing rentings.
     * 
     * @param pSelectedCustomer
     * @param pMoviesToRent
     * @param pRentingDate
     */
    public RentingsFacade(Customer pSelectedCustomer, List<Movie> pReturnedMovies, List<Renting> pRentings, Date pReturnDate) {
        mSelectedCustomer = pSelectedCustomer;
        mRentedMovies = pReturnedMovies;
        mRentings = pRentings;
        mReturnDate = pReturnDate;
    }

    /**
     * Prints an invoice for the rented movies. The invoice is only provisional when movies are rented, the real invoice being printed when
     * movies are returned.
     * 
     * This method is intended to be called by subclasses but is final so that it can't be overridden.
     */
    protected final void printInvoice() {
        StringBuilder lStringBuffer = new StringBuilder();

        appendHeader(lStringBuffer, mReturnDate != null ? mReturnDate : mRentingDate, mSelectedCustomer);
        appendMovies(lStringBuffer, mRentings);
        appendFooter(lStringBuffer, mReturnDate == null);

        PrintUtils.print("Invoice-" + mSelectedCustomer.getAccountNumber() + "-" + System.currentTimeMillis() + ".md", lStringBuffer.toString());
    }

    /**
     * Writes invoice header into buffer.
     * 
     * @param pStringBuffer
     * @param pRentingDate
     * @param pSelectedCustomer
     */
    private void appendHeader(StringBuilder pStringBuffer, Date pRentingDate, Customer pSelectedCustomer) {
        pStringBuffer.append("# Provisional Invoice");
        pStringBuffer.append(NL);
        pStringBuffer.append(NL);
        pStringBuffer.append("**Video 2000**");
        pStringBuffer.append(NL);
        pStringBuffer.append("Some address line");
        pStringBuffer.append(NL);
        pStringBuffer.append("SOMEWHERE");
        pStringBuffer.append(NL);
        pStringBuffer.append(NL);
        pStringBuffer.append("**Date:** ").append(new SimpleDateFormat("yyyy/MM/dd").format(pRentingDate));
        pStringBuffer.append(NL);
        appendCustomerInfo(pStringBuffer, pSelectedCustomer);
    }

    /**
     * Appends customer info to buffer.
     * 
     * @param pStringBuffer
     * @param pSelectedCustomer
     */
    private void appendCustomerInfo(StringBuilder pStringBuffer, Customer pSelectedCustomer) {
        pStringBuffer.append(NL);
        pStringBuffer.append("## Customer");
        pStringBuffer.append(NL);
        pStringBuffer.append("\t**Account Number:** ").append(pSelectedCustomer.getAccountNumber());
        pStringBuffer.append(NL);
        pStringBuffer.append("\t**Name:** ").append(pSelectedCustomer.getName());
        pStringBuffer.append(NL);
        pStringBuffer.append("\t**Address:** ");
        pStringBuffer.append(NL);
        if (pSelectedCustomer.getAddressLine1() != null && pSelectedCustomer.getAddressLine1().trim().length() != 0) {
            pStringBuffer.append("\t\t").append(pSelectedCustomer.getAddressLine1());
            pStringBuffer.append(NL);
        }
        if (pSelectedCustomer.getAddressLine2() != null && pSelectedCustomer.getAddressLine2().trim().length() != 0) {
            pStringBuffer.append("\t\t").append(pSelectedCustomer.getAddressLine2());
            pStringBuffer.append(NL);
        }
        if (pSelectedCustomer.getAddressLine3() != null && pSelectedCustomer.getAddressLine3().trim().length() != 0) {
            pStringBuffer.append("\t\t").append(pSelectedCustomer.getAddressLine3());
            pStringBuffer.append(NL);
        }
        pStringBuffer.append("\t\t").append(pSelectedCustomer.getZipCode()).append(" ").append(pSelectedCustomer.getCity());
        pStringBuffer.append(NL);
        pStringBuffer.append("\t**Credit Card:** ").append(pSelectedCustomer.getCreditCardNumber().substring(0, 4))
                .append(pSelectedCustomer.getCreditCardNumber().substring(4).replaceAll("[^-]", "X"));
        pStringBuffer.append(NL);
    }

    /**
     * Appends movies' info to buffer.
     * 
     * @param pStringBuffer
     * @param pRentings
     */
    private void appendMovies(StringBuilder pStringBuffer, List<Renting> pRentings) {
        pStringBuffer.append(NL);
        pStringBuffer.append("## Rented Movies");
        pStringBuffer.append(NL);
        pStringBuffer.append("<table>");
        pStringBuffer.append(NL);
        pStringBuffer.append("<th><td>Movie</td><td>Price</td></th>");
        pStringBuffer.append(NL);
        int i = 0;
        for (Iterator<Renting> lRentingIterator = pRentings.iterator(); lRentingIterator.hasNext();) {
            Renting lRenting = lRentingIterator.next();
            Movie lMovie = mRentedMovies.get(i);
            pStringBuffer.append("<tr><td>").append(lMovie.getTitle()).append("</td><td>").append(PriceUtils.getRental(lMovie, lRenting.getRentingDate()))
                    .append("</td></tr>");
            pStringBuffer.append(NL);
            i++;
        }
        pStringBuffer.append("</table>");
        pStringBuffer.append(NL);
    }

    /**
     * Appends footer to buffer.
     * 
     * @param pStringBuffer
     * @param pProvisional
     */
    private void appendFooter(StringBuilder pStringBuffer, boolean pProvisional) {
        pStringBuffer.append(NL);
        pStringBuffer.append(NL);
        if (pProvisional) {
            pStringBuffer.append("This is a provisional invoice, and as such is not a binding document. ");
            pStringBuffer.append("Indeed, the Renter will be charged with an additional fee, should he not return the rented movies in due time.");
            pStringBuffer.append(NL);
        }
        pStringBuffer.append("Other legal stuff.");
        pStringBuffer.append(NL);
    }
}
