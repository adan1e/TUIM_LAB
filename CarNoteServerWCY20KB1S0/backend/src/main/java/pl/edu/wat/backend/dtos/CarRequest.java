package pl.edu.wat.backend.dtos;
import lombok.*;
import pl.edu.wat.backend.entities.CollisionEntity;
import pl.edu.wat.backend.entities.RepairEntity;
import pl.edu.wat.backend.entities.TankUpEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarRequest
{
//    private int carID;
    private String model;
    private String make;
    private String color;
//    private Long bestSpeed;
//    private Integer firstMileage;
//    private List<CollisionEntity> collisionEntities = new ArrayList<>();
//    private List<RepairEntity> repairEntities = new ArrayList<>();
//    private List<TankUpEntity> tankUpEntities = new ArrayList<>();
}
