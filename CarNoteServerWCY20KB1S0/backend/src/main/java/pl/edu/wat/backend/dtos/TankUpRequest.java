package pl.edu.wat.backend.dtos;
import lombok.*;
import pl.edu.wat.backend.entities.CarEntity;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TankUpRequest
{
//    private int tankUpID;
    private String tankUpDateString;
    private Integer mileage;
    private Integer liters;
    private Integer cost;
//    private boolean fullTankup;
//    private CarEntity car;
}
