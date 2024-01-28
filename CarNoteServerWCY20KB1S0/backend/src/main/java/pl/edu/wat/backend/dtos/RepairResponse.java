package pl.edu.wat.backend.dtos;
import lombok.*;
import pl.edu.wat.backend.entities.CarEntity;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairResponse
{
    //private int repairID;
    private String repairDateString;
    private Integer mileage;
    private String parts;
    private Integer cost;
    //private CarEntity car;
}
