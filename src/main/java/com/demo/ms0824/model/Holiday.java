package com.demo.ms0824.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Holiday {

    private @Id
    @GeneratedValue Long id;
    private String name;
    private boolean moveableHoliday;
    private int monthOfYear;
    private int dayOfMonth;
    private int dayOfWeek;
    private int earliestDay;
    private int latestDay;

    public Holiday() {
    }

    public Holiday(String name, boolean moveableHoliday, int monthOfYear, int dayOfMonth,
                   int dayOfWeek, int earliestDay, int latestDay) {
        this.name = name;
        this.moveableHoliday = moveableHoliday;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
        this.earliestDay = earliestDay;
        this.latestDay = latestDay;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMoveableHoliday() {
        return moveableHoliday;
    }

    public int getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(int monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getEarliestDay() {
        return earliestDay;
    }

    public void setEarliestDay(int earliestDay) {
        this.earliestDay = earliestDay;
    }

    public int getLatestDay() {
        return latestDay;
    }

    public void setLatestDay(int latestDay) {
        this.latestDay = latestDay;
    }
}
