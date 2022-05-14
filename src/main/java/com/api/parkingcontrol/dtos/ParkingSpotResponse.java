package com.api.parkingcontrol.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpotResponse {

    private UUID id;

    private String block;

    private String brandCar;

    private String modelCar;

    private String colorCar;

    private String apartment;

    private String licensePlateCar;

    private String responsibleName;

    private String parkingSpotNumber;

    private LocalDateTime registrationDate;
}
