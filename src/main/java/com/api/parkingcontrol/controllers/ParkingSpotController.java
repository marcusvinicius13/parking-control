package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotRequest;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.IParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spots")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final IParkingSpotService iParkingSpotService;

    @PostMapping
    public ResponseEntity<Object> createParkingSpot(@RequestBody @Valid ParkingSpotRequest parkingSpotRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(iParkingSpotService.create(parkingSpotRequest));
    }

}
