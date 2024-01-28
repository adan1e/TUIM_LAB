package pl.edu.wat.backend.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tankUp")
public class TankUpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private int tankUpID;
    private String tankUpDate;
    private Integer mileage;
    private Integer liters;
    private Integer cost;
    private boolean fullTankup;


    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    @JoinColumn(name = "car")
    private CarEntity car;

}
