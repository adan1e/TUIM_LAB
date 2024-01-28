package pl.edu.wat.backend.services;

import pl.edu.wat.backend.dtos.TankUpRequest;
import pl.edu.wat.backend.dtos.TankUpResponse;
import pl.edu.wat.backend.entities.TankUpEntity;

import java.util.List;

public interface TankUpService {
    List<TankUpResponse> getAllTankUps();

    TankUpEntity getTankUpById(Long id);

    void addTankUp(TankUpRequest collisionRequest);

    void deleteTankUp(Long id);

    void updateTankUp(Long id, TankUpEntity collisionEntity);
}
