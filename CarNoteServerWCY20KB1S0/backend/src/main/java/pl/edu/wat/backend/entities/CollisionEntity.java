package pl.edu.wat.backend.entities;
import lombok.Data;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;


@Entity
@Data
@Table(name = "collision")
public class CollisionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int collisionID;
    private String collisionDateString;
    private Integer mileage;
    private String plates;
    private String witness;


    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    @JoinColumn(name = "car")
    private CarEntity car;

}
