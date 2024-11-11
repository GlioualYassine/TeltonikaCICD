package com.geolocation.geolocation_api.controllers.paiement;

import com.geolocation.geolocation_api.dto.PaiementRequest;
import com.geolocation.geolocation_api.dto.PaiementResponse;
import com.geolocation.geolocation_api.services.paiement.PaiementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/paiements")
public class PaiementController {

    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @PostMapping("/add")
    public ResponseEntity<PaiementResponse> addPaiement(@RequestBody PaiementRequest paiementRequest) {
        PaiementResponse paiementResponse = paiementService.addPaiement(paiementRequest);
        return ResponseEntity.ok(paiementResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaiementResponse>> getAllPaiements() {
        List<PaiementResponse> paiements = paiementService.getAllPaiements();
        return ResponseEntity.ok(paiements);
    }

    @GetMapping("/{id}")
    public PaiementResponse getPaiementById(@PathVariable UUID id) {
        return paiementService.getPaiementById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PaiementResponse> updatePaiement(@PathVariable UUID id, @RequestBody PaiementRequest paiementRequest) {
        PaiementResponse paiementResponse = paiementService.editPaiement(id, paiementRequest);
        return ResponseEntity.ok(paiementResponse);
    }
}
