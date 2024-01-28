package pl.edu.wat.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.CollisionRequest;
import pl.edu.wat.backend.dtos.CollisionResponse;
import pl.edu.wat.backend.entities.CollisionEntity;
import pl.edu.wat.backend.repositories.CollisionRepository;



import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service
public class CollisionServiceImpl implements CollisionService {

    private final CollisionRepository collisionRepository;

    @Autowired
    public CollisionServiceImpl(CollisionRepository CollisionRepository) {
        this.collisionRepository = CollisionRepository;
    }


    @Override
    public List<CollisionResponse> getAllCollisions()
    {
        return StreamSupport.stream(collisionRepository.findAll().spliterator(), false)
                .map(entity -> new CollisionResponse(entity.getCollisionDateString(), entity.getMileage(),
                        entity.getPlates(), entity.getWitness()))
                .collect(Collectors.toList());

    }

    @Override
    public void addCollision(CollisionRequest collisionRequest)
    {
        CollisionEntity collisionEntity = new CollisionEntity();
        //collisionEntity.setCollisionID(collisionRequest.getCollisionID());
        collisionEntity.setCollisionDateString(collisionRequest.getCollisionDateString());
        collisionEntity.setMileage(collisionRequest.getMileage());
        collisionEntity.setPlates(collisionRequest.getPlates());
        collisionEntity.setWitness(collisionRequest.getWitness());
        collisionRepository.save(collisionEntity);
    }

    @Override
    public void deleteCollision(Long id) {
        collisionRepository.deleteById(id);
    }

    @Override
    public CollisionEntity getCollisionById(Long id) {
        return collisionRepository.findById(id).get();
    }

    @Override
    public void updateCollision(Long id, CollisionEntity collisionEntity) {
        CollisionEntity collision = collisionRepository.findById(id).get();
        collision.setCollisionID(collisionEntity.getCollisionID());
        collision.setCollisionDateString(collisionEntity.getCollisionDateString());
        collision.setMileage(collisionEntity.getMileage());
        collision.setPlates(collisionEntity.getPlates());
        collision.setWitness(collisionEntity.getWitness());
        collisionRepository.save(collision);
    }


}
