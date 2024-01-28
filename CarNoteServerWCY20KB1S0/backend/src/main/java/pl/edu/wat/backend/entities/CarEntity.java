package pl.edu.wat.backend.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Data
@Entity
@Table(name = "car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int carID;
    private String model;
    private String make;
    private String color;



    @JsonManagedReference
    @OneToMany(mappedBy = "car", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private List<CollisionEntity> collisionEntities = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "car", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private List<RepairEntity> repairEntities = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "car", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    private List<TankUpEntity> tankUpEntities = new ArrayList<>();

}
