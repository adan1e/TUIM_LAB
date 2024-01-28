package pl.edu.wat.backend.dtos;
import lombok.*;
import pl.edu.wat.backend.entities.CarEntity;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollisionResponse
{
//    private int CollisionID;
    private String collisionDateString;
    private Integer mileage;
    private String plates;
    private String witness;
//    private CarEntity car;
}
