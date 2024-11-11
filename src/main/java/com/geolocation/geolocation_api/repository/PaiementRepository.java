package com.geolocation.geolocation_api.repository;

import com.geolocation.geolocation_api.entities.Paiement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaiementRepository extends JpaRepository<Paiement, UUID> {

    @Query("SELECT SUM(p.total) FROM Paiement p " +
            "WHERE p.Paid = true " +
            "AND EXTRACT(MONTH FROM p.datepaiement) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND EXTRACT(YEAR FROM p.datepaiement) = EXTRACT(YEAR FROM CURRENT_DATE)")
    Optional<Double> getTotalPaidThisMonth();



    @Query("SELECT SUM(p.total) FROM Paiement p WHERE p.Paid = true")
    Optional<Double> getTotalPaid();


    // Query to fetch the latest payments with a limit
    @Query("SELECT p FROM Paiement p ORDER BY p.datepaiement DESC")
    List<Paiement> findLatestPayments(Pageable pageable);
}

