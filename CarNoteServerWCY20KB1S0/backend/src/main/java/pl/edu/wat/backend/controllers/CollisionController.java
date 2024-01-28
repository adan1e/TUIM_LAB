package pl.edu.wat.backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.backend.dtos.CollisionRequest;
import pl.edu.wat.backend.dtos.CollisionResponse;
import pl.edu.wat.backend.entities.CollisionEntity;
import pl.edu.wat.backend.services.CollisionService;

import java.util.List;

@RestController
public class CollisionController {

    private final CollisionService CollisionService;

    @Autowired
    public CollisionController(CollisionService CollisionService) {
        this.CollisionService = CollisionService;
    }

    @GetMapping("/api/Collision")
    public ResponseEntity<List<CollisionResponse>> getCollisions() {
        List<CollisionResponse> Collisions = CollisionService.getAllCollisions();

        return new ResponseEntity<>(Collisions, HttpStatus.OK);
    }

    @PostMapping("/api/Collision")
    public ResponseEntity addCollision(@RequestBody CollisionRequest CollisionRequest) {
        CollisionService.addCollision(CollisionRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping({"/api/Collision/{ID}"})
    public ResponseEntity<CollisionEntity> getCollision(@PathVariable Long CollisionID) {
        return new ResponseEntity<>(CollisionService.getCollisionById(CollisionID), HttpStatus.OK);
    }


    @PutMapping({"/api/Collision/{ID}"})
    public ResponseEntity<CollisionEntity> updateCollision(@PathVariable("ID") Long CollisionID, @RequestBody CollisionEntity Collision) {
        CollisionService.updateCollision(CollisionID, Collision);
        return new ResponseEntity<>(CollisionService.getCollisionById(CollisionID), HttpStatus.OK);
    }

    @DeleteMapping({"/api/Collision/{ID}"})
    public ResponseEntity<CollisionEntity> deleteCollision(@PathVariable("ID") Long CollisionID) {
        CollisionService.deleteCollision(CollisionID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
