package com.demo.ms0824.repository;

import com.demo.ms0824.model.RentalAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalAgreementRepository extends JpaRepository<RentalAgreement, Long> {
}
