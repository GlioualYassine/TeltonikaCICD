//package com.geolocation.geolocation_api.entities;
//
//import jakarta.annotation.Nullable;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Data
//@Entity @Builder @NoArgsConstructor @AllArgsConstructor
//public class Voiture {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String nom ;
//    private String marque ;
//    private String modele  ;
//    private String immatricule ;
//
//    @OneToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "gpsDevice_id", nullable = true)
//    private GpsDevice gpsDevice ;
//
//    @ManyToOne
//    @Nullable
//    private User user ;
//
//    @Override
//    public String toString() {
//        return "Voiture{" +
//                "id=" + id +
//                ", nom='" + nom + '\'' +
//                ", marque='" + marque + '\'' +
//                ", modele='" + modele + '\'' +
//                ", immatricule='" + immatricule + '\'' +
//                ", gpsDevice=" + gpsDevice +
//                '}';
//    }
//}
