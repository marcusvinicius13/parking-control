package com.api.parkingcontrol.dtos;

import com.api.parkingcontrol.models.ParkingSpotModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotResponse {

    private ParkingSpotModel parkingSpotModel;
}
