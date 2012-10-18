package net.demengel.refactoring.vrs.bean;

import java.util.Date;

/**
 * A movie rented by a customer.
 * 
 * 
 * @author nico 18 oct. 2012
 */
public class Renting {

    /**
     * Customer number
     */
    private String customerNumber;
    /**
     * Movie code
     */
    private String movieCode;
    
    /**
     * Renting date
     */
    private Date rentingDate;
    
    /**
     * Reutrn date.
     */
    private Date returnDate;

    /**
     * @return the customerNumber
     */
    public String getCustomerNumber() {
        return customerNumber;
    }

    /**
     * @param pCustomerNumber
     *            the customerNumber to set
     */
    public void setCustomerNumber(String pCustomerNumber) {
        customerNumber = pCustomerNumber;
    }

    /**
     * @return the movieCode
     */
    public String getMovieCode() {
        return movieCode;
    }

    /**
     * @param pMovieCode
     *            the movieCode to set
     */
    public void setMovieCode(String pMovieCode) {
        movieCode = pMovieCode;
    }

    /**
     * @return the rentingDate
     */
    public Date getRentingDate() {
        return rentingDate;
    }

    /**
     * @param pRentingDate
     *            the rentingDate to set
     */
    public void setRentingDate(Date pRentingDate) {
        rentingDate = pRentingDate;
    }

    /**
     * @return the returnDate
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * @param pReturnDate
     *            the returnDate to set
     */
    public void setReturnDate(Date pReturnDate) {
        returnDate = pReturnDate;
    }

}
