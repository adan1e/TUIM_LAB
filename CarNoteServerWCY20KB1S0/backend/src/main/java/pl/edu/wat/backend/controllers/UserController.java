package pl.edu.wat.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.backend.dtos.CarRequest;
import pl.edu.wat.backend.dtos.CarResponse;
import pl.edu.wat.backend.dtos.UserRequest;
import pl.edu.wat.backend.dtos.UserResponse;
import pl.edu.wat.backend.entities.CarEntity;
import pl.edu.wat.backend.entities.UserEntity;
import pl.edu.wat.backend.services.CarService;
import pl.edu.wat.backend.services.UserService;

import java.util.List;

@RestController
public class UserController {

    private final UserService UserService;

    @Autowired
    public UserController(UserService UserService) {
        this.UserService = UserService;
    }

    @GetMapping("/api/User")
    public ResponseEntity<List<UserResponse>> getCategories() {
        List<UserResponse> categories = UserService.getAllCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping({"/api/User/{ID}"})
    public ResponseEntity<UserEntity> getUser(@PathVariable Long UserID) {
        return new ResponseEntity<>(UserService.getUserById(UserID), HttpStatus.OK);
    }

    @PostMapping("/api/User")
    public ResponseEntity addCar(@RequestBody UserRequest UserRequest) {
        UserService.addUser(UserRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping({"/api/User/{ID}"})
    public ResponseEntity<CarEntity> deleteUser(@PathVariable("ID") Long UserID) {
        UserService.deleteUser(UserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping({"/api/User/{ID}"})
    public ResponseEntity<UserEntity> updateUser(@PathVariable("ID") Long UserID, @RequestBody UserEntity User) {
        UserService.updateUser(UserID, User);
        return new ResponseEntity<>(UserService.getUserById(UserID), HttpStatus.OK);
    }



}
