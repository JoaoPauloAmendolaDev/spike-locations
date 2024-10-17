package com.spike.locationSpike.utils;

import com.spike.locationSpike.entity.LatLong;
import com.spike.locationSpike.repository.PrisonRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HaversineValidator {

    private final PrisonRepository prisonRepository;

    public static final double EARTH_RADIUS_KM = 6371.0;

    public static double calculateHaversineDistance(double userLat, double userLon, double prisonLat, double prisonLon) {
        double deltaLat = Math.toRadians(prisonLat - userLat);
        double deltaLon = Math.toRadians(prisonLon - userLon);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(prisonLat)) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        return EARTH_RADIUS_KM * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    public static boolean isWithinApproximateBounds(double latitude1, double longitude1, double latitude2, double longitude2, double radiusKm) {
        double degreesPerLatitudeKm = 111.0;
        double degreesPerLongitudeKm = 111.0 * Math.cos(Math.toRadians(latitude1));

        return (Math.abs(latitude1 - latitude2) <= radiusKm / degreesPerLatitudeKm &&
                Math.abs(longitude1 - longitude2) <= radiusKm / degreesPerLongitudeKm);
    }

    public boolean isAnyRiskAreaWithinRadius(double userLatitude, double userLongitude, double radiusKm) {
        return prisonRepository.findAllLatLong().stream()
                .map(latLong -> new double[] {
                        Double.parseDouble(latLong[0].toString()),
                        Double.parseDouble(latLong[1].toString())
                })
                .anyMatch(coords -> isWithinApproximateBounds(userLatitude, userLongitude, coords[0], coords[1], radiusKm) &&
                        calculateHaversineDistance(userLatitude, userLongitude, coords[0], coords[1]) <= radiusKm);
    }
}
