package com.example.mapper;


import com.example.entity.Car;
import org.example.rest.model.CarDTO;
import org.mapstruct.*;


@Mapper
public interface CarMapper {

    CarDTO convertToDto(Car car);

    Car convertToCar(CarDTO carDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void updateWholeCar(@MappingTarget Car target, CarDTO source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartiallyCar(@MappingTarget Car target, CarDTO source);

}
