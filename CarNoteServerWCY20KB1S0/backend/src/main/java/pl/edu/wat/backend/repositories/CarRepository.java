package pl.edu.wat.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.backend.entities.CarEntity;

@Repository
public interface CarRepository extends CrudRepository<CarEntity, Long> {
}
