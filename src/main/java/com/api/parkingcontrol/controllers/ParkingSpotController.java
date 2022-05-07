package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.services.IParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spots")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final IParkingSpotService iParkingSpotService;


}
