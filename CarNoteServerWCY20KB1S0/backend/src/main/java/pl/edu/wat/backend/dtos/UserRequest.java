package pl.edu.wat.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest
{
//    private int carID;
    private String login;
    private String password;
//    private Long bestSpeed;
//    private Integer firstMileage;
//    private List<CollisionEntity> collisionEntities = new ArrayList<>();
//    private List<RepairEntity> repairEntities = new ArrayList<>();
//    private List<TankUpEntity> tankUpEntities = new ArrayList<>();
}
