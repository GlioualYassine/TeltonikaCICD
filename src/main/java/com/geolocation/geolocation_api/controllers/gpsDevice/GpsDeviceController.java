package com.geolocation.geolocation_api.controllers.gpsDevice;

import com.geolocation.geolocation_api.entities.GpsDevice;
import com.geolocation.geolocation_api.requests.gpsDevice.GPSDeviceUpdateRequest;
import com.geolocation.geolocation_api.requests.gpsDevice.GpsDeviceRequest;
import com.geolocation.geolocation_api.responses.gpsDevice.GpsDeviceResponse;
import com.geolocation.geolocation_api.services.gpsDevice.GpsDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/gpsDevices")
@RequiredArgsConstructor
public class GpsDeviceController {
    private final GpsDeviceService gpsDeviceService;

    @GetMapping
    public ResponseEntity<List<GpsDeviceResponse>> getGpsDevices() {
        List<GpsDeviceResponse> gpsDevices = gpsDeviceService.findAll();
        return ResponseEntity.ok(gpsDevices);
    }

    @PostMapping
    public ResponseEntity<GpsDeviceResponse> createGpsDevice(
            @RequestBody GpsDeviceRequest gpsDeviceRequest) {
        return ResponseEntity.ok(gpsDeviceService.createGpsDevice(gpsDeviceRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GpsDeviceResponse> getGpsDeviceById(@PathVariable Long id) {
        return ResponseEntity.ok(gpsDeviceService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GpsDeviceResponse> updateGpsDevice(
            @RequestBody GPSDeviceUpdateRequest gpsDeviceUpdateRequestRequest, @PathVariable Long id) {
        return ResponseEntity.ok(gpsDeviceService.updateGpsDevice(gpsDeviceUpdateRequestRequest, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GpsDevice> deleteGpsDevice(@PathVariable Long id) {
        return ResponseEntity.ok(gpsDeviceService.deleteGpsDevice(id));
    }

    @GetMapping("/imei/{imei}")
    public ResponseEntity<GpsDeviceResponse> getGpsDeviceByImei(@PathVariable String imei) {
        return ResponseEntity.ok(gpsDeviceService.findByImei(imei));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GpsDeviceResponse>> getGpsDevicesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(gpsDeviceService.findByUser(userId));
    }
}
