package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dtos.ParkingSpotRequest;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.IParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements IParkingSpotService{

    // Injeção de dependência via construtor, quando for requisitado.
    private final IParkingSpotRepository iParkingSpotRepository;

    @Override
    @Transactional // É importante usar essa anotação, principalmente se tivermos relacionamentos,
    // pois se acontecer algo ele da rollback não deixando inconsistência no banco de dados.
    public ParkingSpotModel create(ParkingSpotRequest parkingSpotRequest) {
        return iParkingSpotRepository
                .save(converterParkingSpotRequestToParkingSpotModelAndSetRegistrationDate(parkingSpotRequest));
    }

    private ParkingSpotModel converterParkingSpotRequestToParkingSpotModelAndSetRegistrationDate(ParkingSpotRequest parkingSpotRequest){
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotRequest, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return parkingSpotModel;
    }
}
