package pl.edu.wat.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.backend.dtos.CarRequest;
import pl.edu.wat.backend.dtos.CarResponse;
import pl.edu.wat.backend.dtos.UserRequest;
import pl.edu.wat.backend.dtos.UserResponse;
import pl.edu.wat.backend.entities.CarEntity;
import pl.edu.wat.backend.entities.UserEntity;
import pl.edu.wat.backend.repositories.CarRepository;
import pl.edu.wat.backend.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRepository userRepository1) {
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
//        carEntity.setCarID(carRequest.getCarID());
        userEntity.setLogin(userRequest.getLogin());
        userEntity.setPassword(userRequest.getPassword());
//        carEntity.setBestSpeed(carRequest.getBestSpeed());
//        carEntity.setFirstMileage(carRequest.getFirstMileage());


        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserResponse> getAllCategories() {

//        return StreamSupport.stream(carRepository.findAll().spliterator(), false)
//                .map(entity -> new CarResponse(entity.getCarID(), entity.getModel(), entity.getMake(), entity.getColor(),
//                        entity.getBestSpeed(),
//                        entity.getFirstMileage(), entity.getCollisionEntities(), entity.getRepairEntities(), entity.getTankUpEntities()))
//                .collect(Collectors.toList());

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(entity -> new UserResponse(entity.getLogin(), entity.getPassword()))
                .collect(Collectors.toList());
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void updateUser(Long id, UserEntity userEntity)
    {
        UserEntity user = userRepository.findById(id).get();
//        car.setCarID(carEntity.getCarID());
        user.setLogin(userEntity.getLogin());
        user.setPassword(userEntity.getPassword());
//        car.setBestSpeed(carEntity.getBestSpeed());
//        car.setFirstMileage(carEntity.getFirstMileage());
        userRepository.save(user);
    }
}
