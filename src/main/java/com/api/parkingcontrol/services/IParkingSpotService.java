package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dtos.ParkingSpotRequest;
import com.api.parkingcontrol.dtos.ParkingSpotResponse;
import com.api.parkingcontrol.models.ParkingSpotModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IParkingSpotService {

    ParkingSpotModel create(ParkingSpotRequest parkingSpotModel);

    boolean existsByLicensePlateCar(String licensePlateCar);

    boolean existsByParkingSpotNumber(String parkingSpotNumber);

    boolean existsByApartmentAndBlock(String apartment, String block);

    List<ParkingSpotResponse> listAll();
    Page<ParkingSpotResponse> listAllPageable(Pageable pageable);

    ParkingSpotResponse findById(UUID id);

    void destroyParkingSpot(UUID id);

    ParkingSpotResponse update(UUID id, ParkingSpotRequest parkingSpotRequest);
}
