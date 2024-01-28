package pl.edu.wat.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.backend.dtos.CarRequest;
import pl.edu.wat.backend.dtos.CarResponse;
import pl.edu.wat.backend.entities.CarEntity;
import pl.edu.wat.backend.repositories.CarRepository;
import pl.edu.wat.backend.services.CarService;

import java.util.List;

@RestController
public class CarController {

    private final CarService CarService;

    @Autowired
    public CarController(CarService CarService) {
        this.CarService = CarService;
    }

    @GetMapping("/api/Car")
    public ResponseEntity<List<CarResponse>> getCategories() {
        List<CarResponse> categories = CarService.getAllCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping({"/api/Car/{ID}"})
    public ResponseEntity<CarEntity> getCar(@PathVariable Long CarID) {
        return new ResponseEntity<>(CarService.getCarById(CarID), HttpStatus.OK);
    }

    @PostMapping("/api/Car")
    public ResponseEntity addCar(@RequestBody CarRequest CarRequest) {
        CarService.addCar(CarRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping({"/api/Car/{ID}"})
    public ResponseEntity<CarEntity> deleteCar(@PathVariable("ID") Long CarID) {
        CarService.deleteCar(CarID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping({"/api/Car/{ID}"})
    public ResponseEntity<CarEntity> updateCar(@PathVariable("ID") Long CarID, @RequestBody CarEntity Car) {
        CarService.updateCar(CarID, Car);
        return new ResponseEntity<>(CarService.getCarById(CarID), HttpStatus.OK);
    }



}
