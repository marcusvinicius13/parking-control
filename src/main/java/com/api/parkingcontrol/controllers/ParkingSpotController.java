package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotRequest;
import com.api.parkingcontrol.dtos.ParkingSpotResponse;
import com.api.parkingcontrol.services.IParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spots")
@RequiredArgsConstructor
public class ParkingSpotController {

    private final IParkingSpotService iParkingSpotService;

    @PostMapping
    public ResponseEntity<Object> createParkingSpot(@RequestBody @Valid ParkingSpotRequest parkingSpotRequest) {
        if(iParkingSpotService.existsByLicensePlateCar(parkingSpotRequest.getLicensePlateCar()))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflict : License Plate Car is already in use! ");

        if(iParkingSpotService.existsByParkingSpotNumber(parkingSpotRequest.getParkingSpotNumber()))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflict : Parking Spot is already in use! ");

        if(iParkingSpotService.existsByApartmentAndBlock(parkingSpotRequest.getApartment(), parkingSpotRequest.getBlock()))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Conflict : Parking Spot already registered for this apartment/block! ");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(iParkingSpotService.create(parkingSpotRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpotResponse> update(@PathVariable(value = "id") UUID id,
                                                          @RequestBody @Valid ParkingSpotRequest parkingSpotRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(iParkingSpotService.update(id, parkingSpotRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> destroy(@PathVariable(value = "id") UUID id) {
        iParkingSpotService.destroyParkingSpot(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Parking Spot deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotResponse>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(iParkingSpotService.listAll());
    }

    @GetMapping("pageable")
    public ResponseEntity<Page<ParkingSpotResponse>> listAllPageable(@PageableDefault(
            page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(iParkingSpotService.listAllPageable(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpotResponse> show(@PathVariable(value = "id")UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(iParkingSpotService.findById(id));
    }


}
