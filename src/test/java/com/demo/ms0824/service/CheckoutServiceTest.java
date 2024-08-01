package com.demo.ms0824.service;

import com.demo.ms0824.constants.Constants;
import com.demo.ms0824.model.Checkout;
import com.demo.ms0824.model.Holiday;
import com.demo.ms0824.model.RentalAgreement;
import com.demo.ms0824.repository.HolidayRepository;
import com.demo.ms0824.repository.RentalAgreementRepository;
import com.demo.ms0824.repository.ToolRepository;
import com.demo.ms0824.repository.ToolTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CheckoutServiceTest {

    private final List<Holiday> holidays = new ArrayList<>();

    @Mock
    private RentalAgreementRepository rentalAgreementRepository;
    @Mock
    private ToolTypeRepository toolTypeRepository;
    @Mock
    private ToolRepository toolRepository;
    @Mock
    private HolidayRepository holidayRepository;

    private CheckoutService checkoutService;

    @BeforeEach
    public void setUp() {
        this.checkoutService = new CheckoutService(rentalAgreementRepository, toolTypeRepository,
                toolRepository, holidayRepository);

        holidays.add(Constants.INDEPENDENCE_DAY);
        holidays.add(Constants.LABOR_DAY);
    }

    @Test
    void createRentalAgreementTEST1() {

        // TEST 1
        Checkout checkout = new Checkout("JAKR", 5, "9/3/15", 101);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            checkoutService.createRentalAgreement(checkout);
        });
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(Constants.EXCEPTION_DISCOUNT_PERCENT));
    }

    @Test
    void createRentalAgreementTEST2() {

        //TEST 2
        Mockito.when(toolRepository.findByCode("LADW")).thenReturn(Constants.LADW_TOOL);
        Mockito.when(toolTypeRepository.findToolTypeByType("Ladder")).thenReturn(Constants.LADDER);
        Mockito.when(holidayRepository.findAll()).thenReturn(holidays);

        Checkout checkout = new Checkout("LADW", 3, "7/2/20", 10);
        RentalAgreement rentalAgreement = checkoutService.createRentalAgreement(checkout);
        assertEquals(1.79, rentalAgreement.getFinalCharge());
    }

    @Test
    void createRentalAgreementTEST3() {

        //TEST 3
        Mockito.when(toolRepository.findByCode("CHNS")).thenReturn(Constants.CHNS_TOOL);
        Mockito.when(toolTypeRepository.findToolTypeByType("Chainsaw")).thenReturn(Constants.CHAINSAW);

        Checkout checkout = new Checkout("CHNS", 5, "7/2/15", 25);
        RentalAgreement rentalAgreement = checkoutService.createRentalAgreement(checkout);
        assertEquals(2.23, rentalAgreement.getFinalCharge());
    }

    @Test
    void createRentalAgreementTEST4() {

        //TEST 4
        Mockito.when(toolRepository.findByCode("JAKD")).thenReturn(Constants.JAKD_TOOL);
        Mockito.when(toolTypeRepository.findToolTypeByType("Jackhammer")).thenReturn(Constants.JACKHAMMER);
        Mockito.when(holidayRepository.findAll()).thenReturn(holidays);

        Checkout checkout = new Checkout("JAKD", 6, "9/3/15", 0);
        RentalAgreement rentalAgreement = checkoutService.createRentalAgreement(checkout);
        assertEquals(5.98, rentalAgreement.getFinalCharge());
    }

    @Test
    void createRentalAgreementTEST5() {

        //TEST 5
        Mockito.when(toolRepository.findByCode("JAKR")).thenReturn(Constants.JAKR_TOOL);
        Mockito.when(toolTypeRepository.findToolTypeByType("Jackhammer")).thenReturn(Constants.JACKHAMMER);
        Mockito.when(holidayRepository.findAll()).thenReturn(holidays);

        Checkout checkout = new Checkout("JAKR", 9, "7/2/15", 0);
        RentalAgreement rentalAgreement = checkoutService.createRentalAgreement(checkout);
        assertEquals(14.95, rentalAgreement.getFinalCharge());
    }

    @Test
    void createRentalAgreementTEST6() {

        //TEST 6
        Mockito.when(toolRepository.findByCode("JAKR")).thenReturn(Constants.JAKR_TOOL);
        Mockito.when(toolTypeRepository.findToolTypeByType("Jackhammer")).thenReturn(Constants.JACKHAMMER);
        Mockito.when(holidayRepository.findAll()).thenReturn(holidays);

        Checkout checkout = new Checkout("JAKR", 4, "7/2/20", 50);
        RentalAgreement rentalAgreement = checkoutService.createRentalAgreement(checkout);
        assertEquals(0.00, rentalAgreement.getFinalCharge());
    }


    @Test
    void getTool() {
        Mockito.when(toolRepository.findByCode("CHNS")).thenReturn(Constants.CHNS_TOOL);
        var toolFromDB = toolRepository.findByCode("CHNS");
        assertEquals("CHNS", toolFromDB.getCode());
        assertEquals("Chainsaw", toolFromDB.getType());
    }

    @Test
    void getToolType() {
        Mockito.when(toolTypeRepository.findToolTypeByType("Ladder")).thenReturn(Constants.LADDER);
        var toolTypeFromDB = toolTypeRepository.findToolTypeByType("Ladder");
        assertEquals("Ladder", toolTypeFromDB.getType());
        assertEquals(1.99, toolTypeFromDB.getDailyCharge());
    }

    @Test
    void getWeekDaysWithStream() {
        LocalDate start = LocalDate.of(2024, 8, 1);
        LocalDate end = LocalDate.of(2024, 8, 15);
        assertEquals(11, checkoutService.getWeekDaysWithStream(start, end));

        start = LocalDate.of(2024, 7, 5);
        end = LocalDate.of(2024, 7, 15);
        assertEquals(7, checkoutService.getWeekDaysWithStream(start, end));
    }

    @Test
    void calculateMoveableHoliday() {
        LocalDate laborDay = LocalDate.of(2024, 9, 2);
        LocalDate memorialDay = LocalDate.of(2024, 5, 27);
        assertEquals(laborDay, checkoutService.calculateMoveableHoliday(9, 1, 1, 7, 2024));
        assertEquals(memorialDay, checkoutService.calculateMoveableHoliday(5, 1, 24, 31, 2024));
    }

    @Test
    void roundHalfUpToCents() {
        assertEquals(2.50, checkoutService.roundHalfUpToCents(2.496));
        assertEquals(0.75, checkoutService.roundHalfUpToCents(0.745));
        assertEquals(0.74, checkoutService.roundHalfUpToCents(0.743));
    }
}