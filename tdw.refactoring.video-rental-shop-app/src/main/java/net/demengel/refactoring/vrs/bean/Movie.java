package net.demengel.refactoring.vrs.bean;

import java.util.Date;
import java.util.Set;


/**
 *   A movie.
 *   
 * @author nico
 * 17 oct. 2012
 * 
 */
public class Movie {

    /**
     * Code
     */
    private String fCode;
    /**
     * Title
     */
    private String fTitle;
    /**
     * Country
     */
    private String fCountry;
    /**
     * Release date
     */
    private Date fReleaseDate;
    /**
     * Release date
     */
    private Date fRentalStart;
    /**
     * Genres
     */
    private Set<String> fGenres;
    /**
     * Movie duration
     */
    private int fDuration;
    /**
     * Movie duration
     */
    private String director;
    /**
     * Writers
     */
    private Set<String> writers;
    /**
     * Cast
     */
    private Set<String> cast;
    /**
     * Forced price
     */
    private Double fForcedPrice;
    /**
     * Quantity
     */
    private int fOwnedQuantity;

    /**
     * @return the code
     */
    public String getCode() {
        return fCode;
    }

    /**
     * @param pCode
     *            the code to set
     */
    public void setCode(String pCode) {
        fCode = pCode;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return fTitle;
    }

    /**
     * @param pTitle
     *            the title to set
     */
    public void setTitle(String pTitle) {
        fTitle = pTitle;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return fCountry;
    }

    /**
     * @param pCountry
     *            the country to set
     */
    public void setCountry(String pCountry) {
        fCountry = pCountry;
    }

    /**
     * @return the releaseDate
     */
    public Date getReleaseDate() {
        return fReleaseDate;
    }

    /**
     * @param pReleaseDate
     *            the releaseDate to set
     */
    public void setReleaseDate(Date pReleaseDate) {
        fReleaseDate = pReleaseDate;
    }

    /**
     * 
     * @return
     */
    public Date getRentalStart() {
        return fRentalStart;
    }
    
    /**
     * 
     * @param pRentalStart
     */
    public void setRentalStart(Date pRentalStart) {
        fRentalStart = pRentalStart;
    }
    /**
     * @return the genres
     */
    public Set<String> getGenres() {
        return fGenres;
    }

    /**
     * @param pGenres
     *            the genres to set
     */
    public void setGenres(Set<String> pGenres) {
        fGenres = pGenres;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return fDuration;
    }

    /**
     * @param pDuration
     *            the duration to set
     */
    public void setDuration(int pDuration) {
        fDuration = pDuration;
    }

    /**
     * @return the director
     */
    public String getDirector() {
        return director;
    }

    /**
     * @param pDirector
     *            the director to set
     */
    public void setDirector(String pDirector) {
        director = pDirector;
    }

    /**
     * @return the writers
     */
    public Set<String> getWriters() {
        return writers;
    }

    /**
     * @param pWriters
     *            the writers to set
     */
    public void setWriters(Set<String> pWriters) {
        writers = pWriters;
    }

    /**
     * @return the cast
     */
    public Set<String> getCast() {
        return cast;
    }

    /**
     * @param pCast
     *            the cast to set
     */
    public void setCast(Set<String> pCast) {
        cast = pCast;
    }

    /**
     * @return the forcedPrice
     */
    public Double getForcedPrice() {
        return fForcedPrice;
    }

    /**
     * @param pForcedPrice
     *            the forcedPrice to set
     */
    public void setForcedPrice(Double pForcedPrice) {
        fForcedPrice = pForcedPrice;
    }

    /**
     * @return the ownedQuantity
     */
    public int getOwnedQuantity() {
        return fOwnedQuantity;
    }

    /**
     * @param pOwnedQuantity
     *            the ownedQuantity to set
     */
    public void setOwnedQuantity(int pOwnedQuantity) {
        fOwnedQuantity = pOwnedQuantity;
    }
}
