package pl.edu.wat.backend.services;

import pl.edu.wat.backend.dtos.CarRequest;
import pl.edu.wat.backend.dtos.CarResponse;
import pl.edu.wat.backend.dtos.UserRequest;
import pl.edu.wat.backend.dtos.UserResponse;
import pl.edu.wat.backend.entities.CarEntity;
import pl.edu.wat.backend.entities.UserEntity;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllCategories();

    void addUser(UserRequest userRequest);

    UserEntity getUserById(Long id);

    void updateUser(Long id, UserEntity userEntity);

    void deleteUser(Long id);
}
