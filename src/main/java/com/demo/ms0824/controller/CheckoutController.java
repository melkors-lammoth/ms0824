package com.demo.ms0824.controller;

import com.demo.ms0824.model.Checkout;
import com.demo.ms0824.model.RentalAgreement;
import com.demo.ms0824.service.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutController {

    private final CheckoutService checkoutService;

    CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<RentalAgreement> createRentalAgreement(@RequestBody Checkout newCheckout) {
        return new ResponseEntity<>(
                checkoutService.createRentalAgreement(newCheckout),
                HttpStatus.OK);
    }
}
