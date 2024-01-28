package pack.sor.carnote.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

import pack.sor.carnote.R;

/**
 * Model danych pojedynczegj kolizji
 */
public class CollisionRecord implements Serializable, ViewHolderAdaptable
{
    /**
     * Data wykonania tankowania
     */
    @JsonProperty("collisionDateString")
    private String collisionDateString;

    private Date collisionDate;

    /**
     * Przebieg auta w momencie tankowania
     */
    @JsonProperty("mileage")
    private Integer mileage;

    /**
     * Tablice rej. dupka
     */
    @JsonProperty("plates")
    private String plates;

    /**
     * Dane o świadku (nr. tel, imie nazw. rej. cokolwiek)
     */
    @JsonProperty("witness")
    private String witness;


    public CollisionRecord(Date collisionDate, Integer mileage, String plates, String witness)
    {
        this.collisionDate = collisionDate;
        this.mileage = mileage;
        this.plates = plates;
        this.witness = witness;
    }

    public CollisionRecord(String collisionDateString, Integer mileage, String plates, String witness)
    {
        this.collisionDateString = collisionDateString;
        this.mileage = mileage;
        this.plates = plates;
        this.witness = witness;
    }

    @Override
    public Date getDate()
    {
        return collisionDate;
    }

    public Integer getMileage()
    {
        return mileage;
    }

    @Override
    public String getFirstText()
    {
        return plates;
    }

    @Override
    public String getSecondText()
    {
        return witness;
    }

    @Override
    public Integer getCategoryDrawable()
    {
        return R.drawable.ic_collision;
    }

    public Date getCollisionDate()
    {
        return collisionDate;
    }

    public void setCollisionDate(Date collisionDate)
    {
        this.collisionDate = collisionDate;
    }

    public void setMileage(Integer mileage)
    {
        this.mileage = mileage;
    }

    public String getPlates()
    {
        return plates;
    }

    public void setPlates(String plates)
    {
        this.plates = plates;
    }

    public String getWitness()
    {
        return witness;
    }

    public void setWitness(String witness)
    {
        this.witness = witness;
    }
}
