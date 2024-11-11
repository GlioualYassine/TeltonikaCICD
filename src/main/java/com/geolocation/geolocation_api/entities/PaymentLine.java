package com.geolocation.geolocation_api.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Paiement paiement;  // Relation avec le paiement

    @ManyToOne
    @JoinColumn(name = "gps_device_id")
    private GpsDevice gpsDevice; // Relation avec l'appareil GPS

    private double tva;         // TVA en pourcentage
    private double unitPrice;   // Prix unitaire de l'appareil
    private double totalPrice;  // Prix total après application de la TVA

    public double calculateTotal() {
        return unitPrice * (1 + tva / 100);
    }
}
