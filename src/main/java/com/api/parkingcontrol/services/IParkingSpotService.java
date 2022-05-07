package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dtos.ParkingSpotRequest;
import com.api.parkingcontrol.models.ParkingSpotModel;

public interface IParkingSpotService {

    ParkingSpotModel create(ParkingSpotRequest parkingSpotModel);
}
