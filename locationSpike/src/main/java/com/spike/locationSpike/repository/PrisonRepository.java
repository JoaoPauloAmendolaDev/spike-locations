package com.spike.locationSpike.repository;

import com.spike.locationSpike.entity.LatLong;
import com.spike.locationSpike.entity.Prison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrisonRepository extends JpaRepository<Prison, Long> {

    @Query("SELECT p.latitude, p.longitude FROM Prison p")
    List<Object[]> findAllLatLong();
}

