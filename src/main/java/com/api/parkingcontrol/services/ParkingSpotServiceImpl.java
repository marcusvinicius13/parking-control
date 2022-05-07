package com.api.parkingcontrol.services;

import com.api.parkingcontrol.repositories.IParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements IParkingSpotService{

    // Injeção de dependência via construtor, quando for requisitado.
    private final IParkingSpotRepository iParkingSpotRepository;
}
