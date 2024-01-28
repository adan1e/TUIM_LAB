package pl.edu.wat.backend.services;

import pl.edu.wat.backend.dtos.CarRequest;
import pl.edu.wat.backend.dtos.CarResponse;
import pl.edu.wat.backend.entities.CarEntity;

import java.util.List;

public interface CarService {
    List<CarResponse> getAllCategories();

    void addCar(CarRequest carRequest);

    CarEntity getCarById(Long id);

    void updateCar(Long id, CarEntity carEntity);

    void deleteCar(Long id);
}
