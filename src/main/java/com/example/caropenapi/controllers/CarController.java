package com.example.controllers;

import com.example.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.rest.controller.CarsApi;
import org.example.rest.model.CarDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController implements CarsApi {

    private final CarService carService;

    @Override
    @SneakyThrows
    public ResponseEntity<CarDTO> createCar(CarDTO carDTO) {
        return ResponseEntity.ok(carService.create(carDTO));
    }

    @Override
    public ResponseEntity<Void> deleteCar(Integer id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<CarDTO> getCar(Integer id) {
        return ResponseEntity.ok(carService.getById(id));
    }

    @Override
    public ResponseEntity<List<CarDTO>> getCars() {
        return ResponseEntity.ok(carService.getAll());
    }

    @Override
    public ResponseEntity<CarDTO> updateCar(Integer id, CarDTO carDTO) {
        return ResponseEntity.ok(carService.updateEntireCar(carDTO,id));
    }

    @Override
    public ResponseEntity<CarDTO> updatePartially(Integer id, CarDTO carDTO) {
        return ResponseEntity.ok(carService.updatePartiallyCar(carDTO,id));
    }

    @Override
    @SneakyThrows
    public ResponseEntity<CarDTO> uploadPhoto(Integer id, MultipartFile photo) {
        String originalFilename = photo.getOriginalFilename();

        File fileForPhoto = new File(System.getProperty("user.home") + File.separator + "images" + File.separator + originalFilename);

        photo.transferTo(fileForPhoto);

        return ResponseEntity.ok(carService.download(id,originalFilename));
    }
}
