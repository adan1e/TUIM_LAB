package pl.edu.wat.backend.dtos;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RepairRequest {
    //private int repairID;
    private String repairDateString;
    private Integer mileage;
    private String parts;
    private Integer cost;
    //private CarEntity car;

}
