package com.api.parkingcontrol.services;

import com.api.parkingcontrol.dtos.ParkingSpotRequest;
import com.api.parkingcontrol.dtos.ParkingSpotResponse;
import com.api.parkingcontrol.exception.BadRequestException;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.IParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    @Transactional // É importante usar essa anotação, principalmente se tivermos relacionamentos,
    // pois se acontecer algo ele da rollback não deixando inconsistência no banco de dados.
    public void destroyParkingSpot(UUID id) {
        final ParkingSpotModel parkingSpotModel = iParkingSpotRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Parking Spot not found to delete"));

        iParkingSpotRepository.delete(parkingSpotModel);
    }

    @Override
    public ParkingSpotResponse update(UUID id, ParkingSpotRequest parkingSpotRequest) {
        final ParkingSpotModel spotModel = iParkingSpotRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Parking Spot not found to update"));

        var parkingSpotModel =  converterParkingSpotRequestToParkingSpotModel(parkingSpotRequest);
        parkingSpotModel.setId(id);
        parkingSpotModel.setRegistrationDate(spotModel.getRegistrationDate());

        return converterParkingSpotModelToParkingSpotResponse(iParkingSpotRepository.save(parkingSpotModel));
    }

    @Override
    public List<ParkingSpotResponse> listAll() {
        return converterParkingSpotModelToParkingSpotResponseMap(iParkingSpotRepository.findAll());
    }

    /**
     *
     * @param id
     * @return Retorna um parking spot convertido, para o modelo de resposta
     */
    @Override
    public ParkingSpotResponse findById(UUID id) {
        return converterParkingSpotModelToParkingSpotResponse(iParkingSpotRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Parking Spot not found")));
    }



    @Override
    public boolean existsByLicensePlateCar(String licensePlateCar) {
        return iParkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    @Override
    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return iParkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    @Override
    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return iParkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    /***
     * @autor Marcus Vinicius
     * @param parkingSpotRequest
     * @return ParkingSpotModel
     * @implNote Método que converte um objeto do tipo ParkingSpotRequest
     *              para ParkingSpotModel e seta a data de registro
     */
    private ParkingSpotModel converterParkingSpotRequestToParkingSpotModelAndSetRegistrationDate(ParkingSpotRequest parkingSpotRequest){
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotRequest, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return parkingSpotModel;
    }

    /***
     * @autor Marcus Vinicius
     * @param parkingSpotRequest
     * @return ParkingSpotModel
     * @implNote Método que converte um objeto do tipo ParkingSpotRequest
     *              para ParkingSpotModel e seta a data de registro
     */
    private ParkingSpotModel converterParkingSpotRequestToParkingSpotModel(ParkingSpotRequest parkingSpotRequest){
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotRequest, parkingSpotModel);
        return parkingSpotModel;
    }

    /***
     * @autor Marcus Vinicius
     * @param parkingSpotResponse
     * @return ParkingSpotModel
     * @implNote Método que converte um objeto do tipo ParkingSpotModel
     *              para ParkingSpotResponse e seta a data de registro
     */
    private ParkingSpotModel converterParkingSpotResponseToParkingSpotModel(ParkingSpotResponse parkingSpotResponse) {
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotResponse, parkingSpotModel);
        return parkingSpotModel;
    }

    /***
     * @autor Marcus Vinicius
     * @param parkingSpotModel
     * @return ParkingSpotModel
     * @implNote Método que converte um objeto do tipo ParkingSpotModel
     *              para ParkingSpotResponse e seta a data de registro
     */
    private ParkingSpotResponse converterParkingSpotModelToParkingSpotResponse(ParkingSpotModel parkingSpotModel){
        var parkingSpotResponse = new ParkingSpotResponse();
        BeanUtils.copyProperties(parkingSpotModel, parkingSpotResponse);
        return parkingSpotResponse;
    }

    /***
     * @autor Marcus Vinicius
     * @param parkingSpotModelList
     * @return ParkingSpotModel
     * @implNote Método que converte uma lista de objetos do tipo ParkingSpotModel
     *              para uma lista do tipo ParkingSpotResponse utilizando o foreach() do stream()
     */
    private List<ParkingSpotResponse> converterParkingSpotModelToParkingSpotResponseForEach(List<ParkingSpotModel> parkingSpotModelList) {
        List<ParkingSpotResponse> parkingSpotResponseList = new ArrayList<>();
        parkingSpotModelList
                .forEach(parkingSpotModel ->
                            parkingSpotResponseList.add(converterParkingSpotModelToParkingSpotResponse(parkingSpotModel)));

        return parkingSpotResponseList;
    }

    /***
     * @autor Marcus Vinicius
     * @param parkingSpotModelList
     * @return ParkingSpotModel
     * @implNote Método que converte uma lista de objetos do tipo ParkingSpotModel
     *              para uma lista do tipo ParkingSpotResponse utilizando o map() do stream()
     */
    private List<ParkingSpotResponse> converterParkingSpotModelToParkingSpotResponseMap(List<ParkingSpotModel> parkingSpotModelList) {
        /* Quando eu referêncio o mesmo elemento, nos dois lados da expressão posso usar lambda para simplificar o código
        return parkingSpotModelList.stream().map(element ->
            converterParkingSpotRequestToParkingSpotModel(element)).collect(Collectors.toList());
        */
        return parkingSpotModelList.stream()
                .map(this::converterParkingSpotModelToParkingSpotResponse).collect(Collectors.toList());
    }

}

