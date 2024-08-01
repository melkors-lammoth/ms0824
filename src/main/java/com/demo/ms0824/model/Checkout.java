package com.demo.ms0824.model;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Checkout {

    private String toolCode;
    private int rentalDays;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M/d/yy")
    private String checkoutDate;
    private int discountPercentage;

    public Checkout(String toolCode, int rentalDays, String checkoutDate, int discountPercentage) {
        this.toolCode = toolCode;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.discountPercentage = discountPercentage;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
