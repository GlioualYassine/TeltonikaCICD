//package com.geolocation.geolocation_api.services.utils;
//
//import com.geolocation.geolocation_api.dto.PaiementRequest;
//import com.geolocation.geolocation_api.dto.PaiementResponse;
//import com.geolocation.geolocation_api.entities.Paiement;
//
//public class PaiementMapper {
//
//    public PaiementResponse toPaiementResponse(Paiement paiement){
//        return PaiementResponse.builder()
//                .id(paiement.getId())
//                .clientId(paiement.getClient().getId())
//                .clientFirstName(paiement.getClient().getFirstName())
//                .clientLastName(paiement.getClient().getLastName())
//                .clientEmail(paiement.getClient().getEmail())
//                .devicesImei(paiement.getDevicesImei())
//                .montantTotal(paiement.getTotal())
//                .subtotal(paiement.getSubtotal())
//                .discount(paiement.getDiscount())
//                .shippingFee(paiement.getShippingFee())
//                .adjustment(paiement.getAdjustment())
//                .dateFrom(paiement.getDatefrom())
//                .dateTo(paiement.getDateto())
//                .datePaiement(paiement.getDatepaiement())
//                .build();
//    }
//
//    public Paiement toPaiement(PaiementRequest paiementRequest){
//
//    }
//}
