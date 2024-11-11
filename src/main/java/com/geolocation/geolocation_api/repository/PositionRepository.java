package com.geolocation.geolocation_api.repository;

import com.geolocation.geolocation_api.entities.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PositionRepository extends JpaRepository<Position,Long> {
    //List<Position> findPositionByImei(String imei);
}