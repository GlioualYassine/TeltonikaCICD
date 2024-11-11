package com.geolocation.geolocation_api.services.dashboard;

import com.geolocation.geolocation_api.dto.CustomerTransactionDTO;
import com.geolocation.geolocation_api.dto.DashboardResponseDTO;
import com.geolocation.geolocation_api.entities.User;
import com.geolocation.geolocation_api.repository.PaiementRepository;
import com.geolocation.geolocation_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardService {


    private final UserRepository userRepository;
    private final PaiementRepository paiementRepository;

    public DashboardResponseDTO getCardSummary() {
        long userCount = userRepository.count();
        double totalPaidThisMonth = paiementRepository.getTotalPaidThisMonth().orElse(0.0);
        double totalPaid = paiementRepository.getTotalPaid().orElse(0.0);
        double averagePayment = (userCount > 0) ? totalPaid / userCount : 0.0;

        Map<String, Double> summary = Map.of(
                "userCount", (double) userCount,
                "totalPaidThisMonth", totalPaidThisMonth,
                "totalPaid", totalPaid,
                "averagePayment", averagePayment
        );

        // Retrieve the latest transactions with logging for debugging
        List<CustomerTransactionDTO> lastCustomers = paiementRepository.findLatestPayments(PageRequest.of(0, 5))
                .stream()
                .map(paiement -> {
                    // Debug log to check paid status
                    String status = paiement.isPaid() ? "Payé" : "Non payé";
                    log.warn("Paiement ID: {}, Paid Status: {}", paiement.getId(), status);
                    return new CustomerTransactionDTO(
                            paiement.getId(),
                            status,
                            paiement.getClient() != null ? paiement.getClient().getEmail() : "N/A",
                            paiement.getClient() != null ? paiement.getClient().getFirstName() +" " +  paiement.getClient().getLastName(): "N/A",
                            paiement.getClient().getId(),
                            paiement.getTotal()
                    );


                })
                .collect(Collectors.toList());
        log.info("test {} " , lastCustomers.get(0));
        return new DashboardResponseDTO(summary, lastCustomers);
    }
}
