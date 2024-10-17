package com.spike.locationSpike.entity;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LatLong {
    private Double latitude;
    private Double longitude;
}
