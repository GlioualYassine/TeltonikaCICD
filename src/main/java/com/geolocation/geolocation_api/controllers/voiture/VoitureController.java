//package com.geolocation.geolocation_api.controllers.voiture;
//
//import com.geolocation.geolocation_api.entities.GpsDevice;
//import com.geolocation.geolocation_api.entities.Voiture;
//import com.geolocation.geolocation_api.repository.GpsDeviceRepository;
//import com.geolocation.geolocation_api.services.gpsDevice.GpsDeviceService;
//import com.geolocation.geolocation_api.services.interfaces.IVoitureService;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("api/v1")
//@RequiredArgsConstructor
//public class VoitureController {
//    private final IVoitureService voitureService;
//    private final GpsDeviceService gpsDeviceService;
//    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);
//
//    @GetMapping("/voitures")
//    public ResponseEntity<List<Voiture>> getVoitures() {
//        return ResponseEntity.ok(voitureService.getVoitures());
//    }
//
//    @GetMapping("/voitures/{id}")
//    public ResponseEntity<Voiture> getVoitureById(@PathVariable Long id) {
//        return ResponseEntity.ok(voitureService.getVoitureById(id));
//    }
//
//    @GetMapping("/voitures/user/{id}")
//    public ResponseEntity<List<Voiture>> getVoitureByUser(@PathVariable Long id) {
//        return ResponseEntity.ok(voitureService.getVoitureByUserId(id));
//    }
//
//    @PostMapping("/voitures")
//    public ResponseEntity<Voiture> createVoiture(@RequestBody Voiture voiture) {
//        log.info("Voiture created {}", voiture);
//        return ResponseEntity.ok(voitureService.createVoiture(voiture));
//    }
//    @PostMapping("/voitures/setGPS/{gpsDeviceId}")
//    public ResponseEntity<Voiture> createVoitureWithGPS(@RequestBody Voiture voiture, @PathVariable Long gpsDeviceId) {
//        GpsDevice gpsDevice = gpsDeviceService.getById(gpsDeviceId);
//        if(gpsDevice == null){
//            log.error("GpsDevice not found");
//            throw new RuntimeException("GpsDevice not found");
//        }
//        return ResponseEntity.ok(voitureService.createVoitureWithGPS(voiture,gpsDevice));
//    }
//
//
//
//    @PatchMapping("voitures/{id}/updateGpsDeviceOfVoiture")
//    public ResponseEntity<Voiture> updateVoitureSetGpsDevice (
//            @PathVariable Long id,
//            @RequestBody GpsDevice gpsDevice) {
//    log.warn("GpsDevice {}", gpsDevice);
//    log.warn("Voiture id {}", id);
//        return ResponseEntity.ok(voitureService.updateVoitureGpsDevice(gpsDevice.getId(),id));
//    }
//
//    @PatchMapping("voitures/{id}/updateGpsDeviceOfVoitureToNull")
//    public ResponseEntity<Voiture> updateVoitureSetGpsDeviceNull (@PathVariable Long id) {
//        return ResponseEntity.ok(voitureService.updateVoitureSetGpsDeviceNull(id));
//    }
//
//
//    @PatchMapping("voitures/{id}")
//    public ResponseEntity<Voiture> updateVoiture (@PathVariable Long id  , @RequestBody Voiture voiture) {
//        return ResponseEntity.ok(voitureService.updateVoiture(voiture , id));
//    }
//
//    @DeleteMapping("/voitures/{id}")
//    public ResponseEntity<Void> deleteVoiture(@PathVariable Long id) {
//        voitureService.deleteVoiture(id);
//        return ResponseEntity.ok().build();
//    }
//}
