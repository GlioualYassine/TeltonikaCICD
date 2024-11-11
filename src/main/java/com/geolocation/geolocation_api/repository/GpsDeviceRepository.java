package com.geolocation.geolocation_api.repository;

import com.geolocation.geolocation_api.entities.GpsDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GpsDeviceRepository extends JpaRepository<GpsDevice,Long> {

    Optional<GpsDevice> findByImei(String imei);

    List<GpsDevice> findByUserId(Long userId);

    ;
}
