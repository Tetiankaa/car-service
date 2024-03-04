package com.example.service;

import com.example.dao.CarDAO;

import com.example.entity.Car;
import com.example.mapper.CarMapper;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.rest.model.CarDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarDAO carDAO;
    private final CarMapper mapper;
    private final MailService mailService;

    @Value("${spring.mail.username}")
    private String receiver;

    public List<CarDTO> getAll(){
        return carDAO.findAll().stream().map(mapper::convertToDto).toList();
    }

    public CarDTO create(CarDTO carDTO) throws MessagingException, IOException {
        Car saved = carDAO.save(mapper.convertToCar(carDTO));

        Path imagePath = Paths.get(System.getProperty("user.home") + File.separator + "images" + File.separator + saved.getPhotoName());

//        mailService.sendEmail(receiver,"Confirmation of saving the car. Here are details about your car: Brand - " + saved.getBrand() + " ; Year: " + saved.getYear() + "; Price: " + saved.getPrice() + " !!!","Successfully saved",Optional.of(imagePath), saved.getPhotoName());
        return mapper.convertToDto(saved);

    }

    public CarDTO getById(Integer id){
        Car car = carDAO.findById(id).orElseThrow();
        return mapper.convertToDto(car);
    }

    public void deleteCar(Integer id ){
            carDAO.deleteById(id);
        }

        public CarDTO updateEntireCar(CarDTO carDTO, Integer targetId){
            Car targetCar = carDAO.findById(targetId).orElseThrow();

            mapper.updateWholeCar(targetCar,carDTO);

            Car saved = carDAO.save(targetCar);

            return mapper.convertToDto(saved);

        }
    public CarDTO updatePartiallyCar(CarDTO carDTO,Integer targetId){

        Car targetCar = carDAO.findById(targetId).orElseThrow();

        mapper.updatePartiallyCar(targetCar,carDTO);

        Car saved = carDAO.save(targetCar);

        return mapper.convertToDto(saved);

    }
    @SneakyThrows
    @Transactional
    public CarDTO download(Integer id, String photoName){
        CarDTO carDTO = new CarDTO();
        carDTO.setPhotoName(photoName);

        Car car = carDAO.findById(id).orElseThrow();

        mapper.updatePartiallyCar(car,carDTO);

        Car saved = carDAO.save(car);

        return mapper.convertToDto(saved);

    }


    }

