package com.geolocation.geolocation_api.repository;

import com.geolocation.geolocation_api.entities.PaymentLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentLineRepository extends JpaRepository<PaymentLine, Long> {
    List<PaymentLine> findByPaiementId(UUID paiementId);
}
