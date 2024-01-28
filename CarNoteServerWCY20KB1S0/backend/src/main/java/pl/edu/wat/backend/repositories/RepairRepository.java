package pl.edu.wat.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.backend.entities.CollisionEntity;
import pl.edu.wat.backend.entities.RepairEntity;

@Repository
public interface RepairRepository extends CrudRepository<RepairEntity, Long> {
}
