package com.demo.ms0824.model;

import com.demo.ms0824.constants.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class RentalAgreement {

    private @Id
    @GeneratedValue Long id;
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private double dailyRentalCharge;
    private long chargeDays;
    private double preDiscountCharge;
    private int discountPercentage;
    private double discountAmount;
    private double finalCharge;

    public RentalAgreement() {
    }

    public Long getId() {
        return id;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public void setToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = LocalDate.parse(checkoutDate, Constants.DATE_PATTERN_INTAKE);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = LocalDate.parse(dueDate, Constants.DATE_PATTERN_INTAKE);
    }

    public double getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public void setDailyRentalCharge(double dailyRentalCharge) {
        this.dailyRentalCharge = dailyRentalCharge;
    }

    public long getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(long chargeDays) {
        this.chargeDays = chargeDays;
    }

    public double getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(double preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(double finalCharge) {
        this.finalCharge = finalCharge;
    }

    public void printRentalAgreement() {
        System.out.println("Tool Code:" + toolCode);
        System.out.println("Tool Type:" + toolType);
        System.out.println("Tool Brand:" + toolBrand);
        System.out.println("Rental Days:" + rentalDays);
        System.out.println("Checkout Date:" + checkoutDate.format(Constants.DATE_PATTERN_PRINT));
        System.out.println("Due Date:" + dueDate.format(Constants.DATE_PATTERN_PRINT));
        System.out.println("Daily Rental Charge:" + Constants.NF_CURRENCY.format(dailyRentalCharge));
        System.out.println("Charge Days:" + chargeDays);
        System.out.println("Pre Discount Charge:" + Constants.NF_CURRENCY.format(preDiscountCharge));
        System.out.println("Discount Percentage:" + Constants.NF_PERCENT.format(discountPercentage / 100f));
        System.out.println("Discount Amount:" + Constants.NF_CURRENCY.format(discountAmount));
        System.out.println("Final Charge:" + Constants.NF_CURRENCY.format(finalCharge));
    }
}
