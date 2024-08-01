package com.demo.ms0824.constants;

import com.demo.ms0824.model.Holiday;
import com.demo.ms0824.model.Tool;
import com.demo.ms0824.model.ToolType;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Constants {

    //Formatting Constants
    public static final DateTimeFormatter DATE_PATTERN_INTAKE = DateTimeFormatter.ofPattern("M/d/yy");
    public static final DateTimeFormatter DATE_PATTERN_PRINT = DateTimeFormatter.ofPattern("MM/dd/yy");
    public static final NumberFormat NF_CURRENCY = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
    public static final NumberFormat NF_PERCENT = NumberFormat.getPercentInstance(new Locale("en", "US"));

    //Holiday Constants
    public static final Holiday INDEPENDENCE_DAY = new Holiday("Independence Day", false, 7, 4, -1, -1, -1);
    public static final Holiday LABOR_DAY = new Holiday("Labor Day", true, 9, -1, 1, 1, 7);

    //Tool Constants
    public static final Tool CHNS_TOOL = new Tool("CHNS", "Chainsaw", "Stihl");
    public static final Tool LADW_TOOL = new Tool("LADW", "Ladder", "Werner");
    public static final Tool JAKD_TOOL = new Tool("JAKD", "Jackhammer", "DeWalt");
    public static final Tool JAKR_TOOL = new Tool("JAKR", "Jackhammer", "Ridgid");

    //Tool Type Constants
    public static final ToolType LADDER = new ToolType("Ladder", 1.99, true, true, false);
    public static final ToolType CHAINSAW = new ToolType("Chainsaw", 1.49, true, false, true);
    public static final ToolType JACKHAMMER = new ToolType("Jackhammer", 2.99, true, false, false);

    //Exception Constants
    public static final String EXCEPTION_RENTAL_DAY = "Please enter at least one rental day";
    public static final String EXCEPTION_DISCOUNT_PERCENT = "Discount percentage must be between 0 and 100";

    private Constants() {
    }


}
