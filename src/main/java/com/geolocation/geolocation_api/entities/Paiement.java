package com.geolocation.geolocation_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Paiement {
    @Id
    private UUID id ;
    @ManyToOne
    private User client ;
    private Date datepaiement ;

    private Date datefrom ;
    private Date dateto ;
    private double subtotal;
    private double discount;
    private double shippingFee;
    private double adjustment;
    private double total;
    @Column(nullable = true)
    public boolean Paid=false;

    @OneToMany(mappedBy = "paiement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentLine> paymentLines;  // Liste de lignes de paiement associées à ce paiement

    // Calcul du total avec les lignes de paiement
    public double calculateTotal() {
        double totalLineItems = paymentLines.stream()
                .mapToDouble(PaymentLine::calculateTotal)
                .sum();
        double discountAmount = subtotal * (discount / 100);
        return subtotal + totalLineItems + shippingFee - discountAmount + adjustment;
    }

}
