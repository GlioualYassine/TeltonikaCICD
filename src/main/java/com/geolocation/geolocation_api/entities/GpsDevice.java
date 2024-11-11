package com.geolocation.geolocation_api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class GpsDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ServerType serverType;

    @Column(length = 100)
    private String description;

    private boolean actif;

    @Column(length = 50 , unique = true)

    private String imei;

    @Column(length = 50)
    private String nom;

    @Column(length = 15)
    private String simNumber1;

    @Column(length = 15)
    private String simNumber2;

    @Column(length = 50)
    private String smsEmail;

    private double vitesseMax;

    // Informations de la voiture



    @Column(length = 50)
    private String marque;         // Marque de la voiture

    @Column(length = 50)
    private String modele;         // Modèle de la voiture

    @Column(length = 50)
    private String immatricule;    // Numéro d'immatriculation

    // Dates de maintenance
    private LocalDate insuranceExpiryDate;    // Date d'expiration de l'assurance
    private LocalDate oilChangeDate;          // Date de la prochaine vidange
    private LocalDate vignetteDeadline;       // Date limite de la vignette

    // Champs supplémentaires
    private double fuelLevel;                 // Niveau de carburant
    private boolean speedAlertEnabled;        // Alerte pour vitesse excessive activée
    private boolean fuelAlertEnabled;         // Alerte pour niveau de carburant bas activée
    private boolean hoodOpenAlertEnabled;     // Alerte pour ouverture du capot activée
    private boolean deviceDeactivationAlert;  // Alerte pour désactivation du boîtier activée
    private boolean paymentPending;           // Statut du paiement en attente

    // Statut de connexion du boîtier
    private boolean deviceConnected = true;          // Statut de la connexion de l'appareil GPS

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;                        // Relation avec un utilisateur

    @CreatedDate
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "gpsDevice")
    private List<PaymentLine> paymentLines;  // Historique des lignes de paiement pour cet appareil
}
