package com.geolocation.geolocation_api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column( nullable = true)
    private String latitude;

    @Column( nullable = true)
    private String longitude;

    @Column( nullable = false)
    private String speed;

    @Column( nullable = false)
    private String imei;

    // Timestamp cratedAt and updatedAt



    public Position(String latitude, String longitude, String speed, String imei) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.imei = imei;
    }


    @Override
    public String toString() {
        return "Position{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", speed='" + speed + '\'' +
                ", imei='" + imei + '\'' +
                '}';
    }
}