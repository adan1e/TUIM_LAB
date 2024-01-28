package pack.sor.carnote.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model danych auta
 */

public class AutoData implements Serializable
{
    /**
     * Model auta, np. Golf
     */
    @JsonProperty("model")
    private String model;
    /**
     * Marka auta, np. VolksWagen
     */
    @JsonProperty("make")
    private String make;
    /**
     * Kolor auta
     */
    @JsonProperty("color")
    private String color;
    /**
     * Rekord prędkości
     */
    private Long bestSpeed = 0L;

    /**
     * Pierwszy przebieg
     */
    private Integer firstMileage = 0;

    /**
     * Kolekcja tankowań
     */
    private List<TankUpRecord> tankUpRecords = new ArrayList<>();

    /**
     * Kolekcja napraw
     */
    private List<RepairRecord> repairRecords = new ArrayList<>();

    /**
     * Kolekcja kolizji
     */
    private List<CollisionRecord> collisionRecords = new ArrayList<>();

    public AutoData(String model, String make, String color)
    {
        this.model = model;
        this.make = make;
        this.color = color;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getMake()
    {
        return make;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public List<TankUpRecord> getTankUpRecords()
    {
        return tankUpRecords;
    }

    public void setTankUpRecords(List<TankUpRecord> tankUpRecords)
    {
        this.tankUpRecords = tankUpRecords;
    }

    public List<RepairRecord> getRepairRecords()
    {
        return repairRecords;
    }

    public void setRepairRecords(List<RepairRecord> repairRecords)
    {
        this.repairRecords = repairRecords;
    }

    public List<CollisionRecord> getCollisionRecords()
    {
        return collisionRecords;
    }

    public void setCollisionRecords(List<CollisionRecord> collisionRecords)
    {
        this.collisionRecords = collisionRecords;
    }

    public Long getBestSpeed()
    {
        return bestSpeed;
    }

    public void setBestSpeed(Long bestSpeed)
    {
        this.bestSpeed = bestSpeed;
    }

    public Integer getFirstMileage()
    {
        return firstMileage;
    }

    public void setFirstMileage(Integer firstMileage)
    {
        this.firstMileage = firstMileage;
    }

    @Override
    public String toString()
    {
        return make + " " + model + " " + color;
    }
}
