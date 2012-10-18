package net.demengel.refactoring.vrs.bean;

import java.util.Date;

/**
 * A customer
 * 
 * @author nico 17 oct. 2012
 */
public class Customer {

    /**
     * Accoutn number
     */
    private String accountNumber;
    /**
     * Customer name
     */
    private String name;
    /**
     * Customer birth date
     */
    private Date birthDate;
    /**
     * Address line #1
     */
    private String addressLine1;
    /**
     * Address line #2
     */
    private String addressLine2;
    /**
     * Address line #2
     */
    private String addressLine3;
    /**
     * City
     */
    private String city;
    /**
     * Zip code
     */
    private String zipCode;
    /**
     * Phone number
     */
    private String phoneNumber;
    /**
     * Credit card number
     */
    private String creditCardNumber;
    /**
     * Customer credits
     */
    private int credits;
    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }
    /**
     * @return the addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }
    /**
     * @return the addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }
    /**
     * @return the addressLine3
     */
    public String getAddressLine3() {
        return addressLine3;
    }
    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }
    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }
    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * @return the creditCardNumber
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }
    /**
     * @return the credits
     */
    public int getCredits() {
        return credits;
    }
    /**
     * @param pAccountNumber the accountNumber to set
     */
    public void setAccountNumber(String pAccountNumber) {
        accountNumber = pAccountNumber;
    }
    /**
     * @param pName the name to set
     */
    public void setName(String pName) {
        name = pName;
    }
    /**
     * @param pBirthDate the birthDate to set
     */
    public void setBirthDate(Date pBirthDate) {
        birthDate = pBirthDate;
    }
    /**
     * @param pAddressLine1 the addressLine1 to set
     */
    public void setAddressLine1(String pAddressLine1) {
        addressLine1 = pAddressLine1;
    }
    /**
     * @param pAddressLine2 the addressLine2 to set
     */
    public void setAddressLine2(String pAddressLine2) {
        addressLine2 = pAddressLine2;
    }
    /**
     * @param pAddressLine3 the addressLine3 to set
     */
    public void setAddressLine3(String pAddressLine3) {
        addressLine3 = pAddressLine3;
    }
    /**
     * @param pCity the city to set
     */
    public void setCity(String pCity) {
        city = pCity;
    }
    /**
     * @param pZipCode the zipCode to set
     */
    public void setZipCode(String pZipCode) {
        zipCode = pZipCode;
    }
    /**
     * @param pPhoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String pPhoneNumber) {
        phoneNumber = pPhoneNumber;
    }
    /**
     * @param pCreditCardNumber the creditCardNumber to set
     */
    public void setCreditCardNumber(String pCreditCardNumber) {
        creditCardNumber = pCreditCardNumber;
    }
    /**
     * @param pCredits the credits to set
     */
    public void setCredits(int pCredits) {
        credits = pCredits;
    }
    
    
}
