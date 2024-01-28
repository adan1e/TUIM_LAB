package pl.edu.wat.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.backend.entities.CarEntity;
import pl.edu.wat.backend.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
