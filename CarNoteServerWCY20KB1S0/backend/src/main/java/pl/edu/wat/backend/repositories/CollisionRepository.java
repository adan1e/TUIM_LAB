package pl.edu.wat.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.backend.entities.CollisionEntity;

@Repository
public interface CollisionRepository extends CrudRepository<CollisionEntity, Long> {
}
