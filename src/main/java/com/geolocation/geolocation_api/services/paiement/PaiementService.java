package com.geolocation.geolocation_api.services.paiement;

import com.geolocation.geolocation_api.dto.DevicePaymentInfo;
import com.geolocation.geolocation_api.dto.PaiementRequest;
import com.geolocation.geolocation_api.dto.PaiementResponse;
import com.geolocation.geolocation_api.dto.PaymentLineResponse;
import com.geolocation.geolocation_api.entities.GpsDevice;
import com.geolocation.geolocation_api.entities.Paiement;
import com.geolocation.geolocation_api.entities.PaymentLine;

import com.geolocation.geolocation_api.repository.GpsDeviceRepository;
import com.geolocation.geolocation_api.repository.PaiementRepository;
import com.geolocation.geolocation_api.repository.PaymentLineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
public class PaiementService {

    private final GpsDeviceRepository gpsDeviceRepository;
    private final PaiementRepository paiementRepository;
    private final PaymentLineRepository paymentLineRepository;

    public PaiementService(GpsDeviceRepository gpsDeviceRepository,
                           PaiementRepository paiementRepository,
                           PaymentLineRepository paymentLineRepository) {
        this.gpsDeviceRepository = gpsDeviceRepository;
        this.paiementRepository = paiementRepository;
        this.paymentLineRepository = paymentLineRepository;
    }

    @Transactional
    public PaiementResponse addPaiement(PaiementRequest paiementRequest) {
        // Create and save the Paiement
        Paiement paiement = Paiement.builder()
                .id(UUID.randomUUID())
                .datepaiement(paiementRequest.datePaiement())
                .datefrom(paiementRequest.dateFrom())
                .dateto(paiementRequest.dateTo())
                .subtotal(paiementRequest.subtotal())
                .discount(paiementRequest.discount())
                .shippingFee(paiementRequest.shippingFee())
                .adjustment(paiementRequest.adjustment())
                .Paid(false)
                .build();
        paiement = paiementRepository.save(paiement);

        // Create PaymentLines in a loop
        List<PaymentLine> paymentLines = new ArrayList<>();
        for (DevicePaymentInfo deviceInfo : paiementRequest.devices()) {
            GpsDevice gpsDevice = gpsDeviceRepository.findByImei(deviceInfo.imei())
                    .orElseThrow(() -> new IllegalArgumentException("GPS device with IMEI " + deviceInfo.imei() + " not found"));
            paiement.setClient(gpsDevice.getUser());
            double totalPrice = deviceInfo.unitPrice() * (1 + deviceInfo.tva() / 100);
            PaymentLine paymentLine = PaymentLine.builder()
                    .paiement(paiement)
                    .gpsDevice(gpsDevice)
                    .unitPrice(deviceInfo.unitPrice())
                    .tva(deviceInfo.tva())
                    .totalPrice(totalPrice)
                    .build();
            paymentLines.add(paymentLine);
        }
        paymentLineRepository.saveAll(paymentLines);
        paiement.setPaymentLines(paymentLines);

        // Calculate and set total amount for Paiement
        double totalLineItems = paymentLines.stream().mapToDouble(PaymentLine::getTotalPrice).sum();
        double discountAmount = paiementRequest.subtotal() * (paiementRequest.discount() / 100);
        paiement.setTotal(paiementRequest.subtotal() + totalLineItems + paiementRequest.shippingFee() - discountAmount + paiementRequest.adjustment());
        paiementRepository.save(paiement);

        return convertToPaiementResponse(paiement);
    }

    public List<PaiementResponse> getAllPaiements() {
        return paiementRepository.findAll().stream()
                .map(this::convertToPaiementResponse)
                .collect(Collectors.toList());
    }

    private PaiementResponse convertToPaiementResponse(Paiement paiement) {
        List<PaymentLineResponse> paymentLineResponses = paiement.getPaymentLines().stream()
                .map(line -> new PaymentLineResponse(line.getId(), line.getGpsDevice().getImei(),
                        line.getUnitPrice(), line.getTva(), line.getTotalPrice()))
                .collect(Collectors.toList());
        return new PaiementResponse(paiement.getId(), paiement.getClient().getId(), paiement.getClient().getFirstName() + " " + paiement.getClient().getLastName(),
                paiement.getDatepaiement(), paiement.getDatefrom(), paiement.getDateto(),
                paiement.getSubtotal(), paiement.getDiscount(), paiement.getShippingFee(),
                paiement.getAdjustment(), paiement.getTotal(), paiement.isPaid(),paymentLineResponses);
    }

    public PaiementResponse getPaiementById(UUID id) {
        Optional<Paiement> paiement = paiementRepository.findById(id);
        if (paiement.isEmpty()) {
            throw new IllegalArgumentException("Paiement with ID " + id + " not found");
        }
        return convertToPaiementResponse(paiement.get());
    }

    @Transactional
    public PaiementResponse editPaiement(UUID id, PaiementRequest updatedPaiementRequest) {
        Paiement existingPaiement = paiementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paiement with ID " + id + " not found"));

        log.info("Updating Paiement with ID {}", updatedPaiementRequest);
        // Update main Paiement details
        existingPaiement.setDatepaiement(updatedPaiementRequest.datePaiement());
        existingPaiement.setDatefrom(updatedPaiementRequest.dateFrom());
        existingPaiement.setDateto(updatedPaiementRequest.dateTo());
        existingPaiement.setSubtotal(updatedPaiementRequest.subtotal());
        existingPaiement.setDiscount(updatedPaiementRequest.discount());
        existingPaiement.setShippingFee(updatedPaiementRequest.shippingFee());
        existingPaiement.setAdjustment(updatedPaiementRequest.adjustment());
        existingPaiement.setPaid(updatedPaiementRequest.paid());
        // Update PaymentLines
        List<PaymentLine> existingPaymentLines = existingPaiement.getPaymentLines();

        // Track GPS devices and payment lines to update
        Set<String> updatedDeviceImes = new HashSet<>();

        for (DevicePaymentInfo deviceInfo : updatedPaiementRequest.devices()) {
            GpsDevice gpsDevice = gpsDeviceRepository.findByImei(deviceInfo.imei())
                    .orElseThrow(() -> new IllegalArgumentException("GPS device with IMEI " + deviceInfo.imei() + " not found"));

            double totalPrice = deviceInfo.unitPrice() * (1 + deviceInfo.tva() / 100);

            // Check if a line already exists for this device
            PaymentLine paymentLine = existingPaymentLines.stream()
                    .filter(line -> line.getGpsDevice().equals(gpsDevice))
                    .findFirst()
                    .orElseGet(() -> {
                        PaymentLine newLine = new PaymentLine();
                        newLine.setGpsDevice(gpsDevice);
                        newLine.setPaiement(existingPaiement);
                        existingPaymentLines.add(newLine); // Add to the existing collection
                        return newLine;
                    });

            // Update the existing line
            paymentLine.setUnitPrice(deviceInfo.unitPrice());
            paymentLine.setTva(deviceInfo.tva());
            paymentLine.setTotalPrice(totalPrice);
            updatedDeviceImes.add(deviceInfo.imei());
        }

        // Remove orphaned payment lines
        existingPaymentLines.removeIf(line -> !updatedDeviceImes.contains(line.getGpsDevice().getImei()));

        // Save updated PaymentLines
        paymentLineRepository.saveAll(existingPaymentLines);

        // Recalculate the total amount
        double totalLineItems = existingPaymentLines.stream().mapToDouble(PaymentLine::getTotalPrice).sum();
        double discountAmount = existingPaiement.getSubtotal() * (existingPaiement.getDiscount() / 100);
        existingPaiement.setTotal(existingPaiement.getSubtotal() + totalLineItems + existingPaiement.getShippingFee() - discountAmount + existingPaiement.getAdjustment());

        // Save the updated Paiement
        paiementRepository.save(existingPaiement);

        // Return the updated Paiement as a response
        return convertToPaiementResponse(existingPaiement);
    }


}
