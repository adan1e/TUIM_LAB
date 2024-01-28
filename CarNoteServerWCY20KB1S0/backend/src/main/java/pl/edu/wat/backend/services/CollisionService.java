package pl.edu.wat.backend.services;

import pl.edu.wat.backend.dtos.CollisionRequest;
import pl.edu.wat.backend.dtos.CollisionResponse;
import pl.edu.wat.backend.entities.CollisionEntity;

import java.util.List;

public interface CollisionService {
    List<CollisionResponse> getAllCollisions();

    CollisionEntity getCollisionById(Long id);

    void addCollision(CollisionRequest collisionRequest);

    void deleteCollision(Long id);

    void updateCollision(Long id, CollisionEntity collisionEntity);
}
