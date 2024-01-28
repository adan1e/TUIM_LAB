package pl.edu.wat.backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.backend.dtos.TankUpRequest;
import pl.edu.wat.backend.dtos.TankUpResponse;
import pl.edu.wat.backend.entities.TankUpEntity;
import pl.edu.wat.backend.services.TankUpService;

import java.util.List;

@RestController
public class TankUpController {

    private final TankUpService TankUpService;

    @Autowired
    public TankUpController(TankUpService TankUpService) {
        this.TankUpService = TankUpService;
    }

    @GetMapping("/api/TankUp")
    public ResponseEntity<List<TankUpResponse>> getTankUps() {
        List<TankUpResponse> TankUps = TankUpService.getAllTankUps();

        return new ResponseEntity<>(TankUps, HttpStatus.OK);
    }

    @PostMapping("/api/TankUp")
    public ResponseEntity addTankUp(@RequestBody TankUpRequest TankUpRequest) {
        TankUpService.addTankUp(TankUpRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping({"/api/TankUp/{ID}"})
    public ResponseEntity<TankUpEntity> getTankUp(@PathVariable Long TankUpID) {
        return new ResponseEntity<>(TankUpService.getTankUpById(TankUpID), HttpStatus.OK);
    }


    @PutMapping({"/api/TankUp/{ID}"})
    public ResponseEntity<TankUpEntity> updateTankUp(@PathVariable("ID") Long TankUpID, @RequestBody TankUpEntity TankUp) {
        TankUpService.updateTankUp(TankUpID, TankUp);
        return new ResponseEntity<>(TankUpService.getTankUpById(TankUpID), HttpStatus.OK);
    }

    @DeleteMapping({"/api/TankUp/{ID}"})
    public ResponseEntity<TankUpEntity> deleteTankUp(@PathVariable("ID") Long TankUpID) {
        TankUpService.deleteTankUp(TankUpID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
