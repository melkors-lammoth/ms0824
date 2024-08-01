package com.demo.ms0824.service;

import com.demo.ms0824.constants.Constants;
import com.demo.ms0824.model.*;
import com.demo.ms0824.repository.HolidayRepository;
import com.demo.ms0824.repository.RentalAgreementRepository;
import com.demo.ms0824.repository.ToolRepository;
import com.demo.ms0824.repository.ToolTypeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class CheckoutService {

    private final RentalAgreementRepository rentalAgreementRepository;
    private final ToolTypeRepository toolTypeRepository;
    private final ToolRepository toolRepository;
    private final HolidayRepository holidayRepository;

    public CheckoutService(RentalAgreementRepository rentalAgreementRepository,
                           ToolTypeRepository toolTypeRepository, ToolRepository toolRepository,
                           HolidayRepository holidayRepository) {
        this.rentalAgreementRepository = rentalAgreementRepository;
        this.toolTypeRepository = toolTypeRepository;
        this.toolRepository = toolRepository;
        this.holidayRepository = holidayRepository;
    }

    public RentalAgreement createRentalAgreement(Checkout checkout) {

        if (checkout.getRentalDays() < 1) {
            throw new IllegalArgumentException(Constants.EXCEPTION_RENTAL_DAY);
        }

        if (checkout.getDiscountPercentage() < 0 || checkout.getDiscountPercentage() > 100) {
            throw new IllegalArgumentException(Constants.EXCEPTION_DISCOUNT_PERCENT);
        }

        RentalAgreement rentalAgreement = new RentalAgreement();
        rentalAgreement.setCheckoutDate(checkout.getCheckoutDate());
        rentalAgreement.setDiscountPercentage(checkout.getDiscountPercentage());
        rentalAgreement.setRentalDays(checkout.getRentalDays());
        rentalAgreement.setToolCode(checkout.getToolCode());

        Tool tool = getTool(checkout.getToolCode());
        rentalAgreement.setToolType(tool.getType());
        rentalAgreement.setToolBrand(tool.getBrand());

        ToolType toolType = getToolType(tool.getType());
        rentalAgreement.setDailyRentalCharge(toolType.getDailyCharge());

        LocalDate checkoutDate = LocalDate.parse(checkout.getCheckoutDate(), Constants.DATE_PATTERN_INTAKE);
        //we subtract 1 from rental days since we include the checkout date as a rental day
        LocalDate dueDate = checkoutDate.plusDays(checkout.getRentalDays() - 1);
        rentalAgreement.setDueDate(dueDate.format(Constants.DATE_PATTERN_INTAKE));

        //we subtract 1 from rental days since charge days begin the day after checkout date
        long chargeDays = checkout.getRentalDays() - 1;

        if (!toolType.isWeekendCharge()) {
            //calculate weekdays
            //we add 1 from checkout date since charge days begin the day after checkout date
            chargeDays = getWeekDaysWithStream(checkoutDate.plusDays(1), dueDate);
        }

        if (!toolType.isHolidayCharge()) {
            //calculate days without holidays
            List<Holiday> holidays = holidayRepository.findAll();

            for (Holiday holiday : holidays) {
                LocalDate holidayDate;

                //For this demo we are assuming that the rental will occur in the same year
                if (holiday.isMoveableHoliday()) {
                    holidayDate = calculateMoveableHoliday(holiday.getMonthOfYear(), holiday.getDayOfWeek(),
                            holiday.getEarliestDay(), holiday.getLatestDay(), checkoutDate.getYear());
                } else {
                    holidayDate = LocalDate.of(checkoutDate.getYear(), holiday.getMonthOfYear(), holiday.getDayOfMonth());
                }

                if (holidayDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                    holidayDate.minusDays(1);
                } else if (holidayDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    holidayDate.plusDays(1);
                }

                if (holidayDate.isAfter(checkoutDate.plusDays(1)) && holidayDate.isBefore(dueDate)
                        || holidayDate.equals(checkoutDate.plusDays(1)) || holidayDate.equals(dueDate)) {
                    --chargeDays;
                }
            }
        }

        rentalAgreement.setChargeDays(chargeDays);

        //calculate charges
        double preDiscountCharge = roundHalfUpToCents(chargeDays * rentalAgreement.getDailyRentalCharge());
        double discountAmount = roundHalfUpToCents(preDiscountCharge * (rentalAgreement.getDiscountPercentage() * .01));
        double finalCharge = roundHalfUpToCents(preDiscountCharge - discountAmount);

        rentalAgreement.setPreDiscountCharge(preDiscountCharge);
        rentalAgreement.setDiscountAmount(discountAmount);
        rentalAgreement.setFinalCharge(finalCharge);

        rentalAgreementRepository.save(rentalAgreement);
        rentalAgreement.printRentalAgreement();
        return rentalAgreement;
    }

    public Tool getTool(String code) {
        return toolRepository.findByCode(code);
    }

    public ToolType getToolType(String type) {
        return toolTypeRepository.findToolTypeByType(type);
    }

    public long getWeekDaysWithStream(LocalDate start, LocalDate end) {
        // we add 1 to the end date because the end date in datesUntil is exclusive
        end = end.plusDays(1);
        return start.datesUntil(end)
                .map(LocalDate::getDayOfWeek)
                .filter(day -> !Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(day))
                .count();
    }

    public LocalDate calculateMoveableHoliday(int month, int dayOfWeek, int earliestDay, int latestDay, int year) {
        LocalDate start = LocalDate.of(year, month, earliestDay);
        LocalDate end = LocalDate.of(year, month, latestDay);
        // we add 1 to the end date because the end date in datesUntil is exclusive
        end = end.plusDays(1);

        List<LocalDate> listOfDates = start.datesUntil(end)
                .filter(date -> date.getDayOfWeek().getValue() == dayOfWeek)
                .toList();

        return listOfDates.getFirst();
    }

    public double roundHalfUpToCents(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
