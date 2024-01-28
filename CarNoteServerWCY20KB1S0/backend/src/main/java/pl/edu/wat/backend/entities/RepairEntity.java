package pl.edu.wat.backend.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Repair")
public class RepairEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int repairID;
    private String repairDate;
    private Integer mileage;
    private String parts;
    private Integer cost;
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    @JoinColumn(name = "car")
    private CarEntity car;
}
