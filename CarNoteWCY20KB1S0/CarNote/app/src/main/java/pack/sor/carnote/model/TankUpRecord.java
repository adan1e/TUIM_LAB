package pack.sor.carnote.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import pack.sor.carnote.R;

/**
 * Model danych pojedynczego tankowania
 */
public class TankUpRecord implements Serializable, ViewHolderAdaptable, CostCountable
{
    /**
     * Data wykonania tankowania
     */
    private Date tankUpDate;

    @JsonProperty("tankUpDateString")
    private String tankUpDateString;

    /**
     * Przebieg auta w momencie tankowania
     */
    @JsonProperty("mileage")
    private Integer mileage;

    /**
     * Litry dolane
     */
    @JsonProperty("liters")
    private Integer liters;

    /**
     * Zapłacona kwota
     */
    @JsonProperty("cost")
    private Integer cost;

    /**
     * Czy tankowanie było do pełna?
     */
    private boolean fullTankup;



    public TankUpRecord(Date tankUpDate, Integer mileage, Integer liters, Integer cost, boolean fullTankup)
    {
        this.tankUpDate = tankUpDate;
        this.mileage = mileage;
        this.liters = liters;
        this.cost = cost;
        this.fullTankup = fullTankup;
    }

    public TankUpRecord(String tankUpDateString, Integer mileage, Integer liters, Integer cost) {
        this.tankUpDateString = tankUpDateString;
        this.mileage = mileage;
        this.liters = liters;
        this.cost = cost;
    }

    @Override
    public Date getDate()
    {
        return tankUpDate;
    }

    public Integer getMileage()
    {
        return mileage;
    }

    @Override
    public String getFirstText()
    {
        return String.valueOf(liters) + " L";
    }

    @Override
    public String getSecondText()
    {
        Currency currency = Currency.getInstance(Locale.getDefault());
        return String.valueOf(cost + " " + currency.getCurrencyCode());
    }

    @Override
    public Integer getCategoryDrawable()
    {
        return R.drawable.ic_new_tankup;
    }

    public Date getTankUpDate()
    {
        return tankUpDate;
    }

    public void setTankUpDate(Date tankUpDate)
    {
        this.tankUpDate = tankUpDate;
    }

    public void setMileage(Integer mileage)
    {
        this.mileage = mileage;
    }

    public Integer getLiters()
    {
        return liters;
    }

    public void setLiters(Integer liters)
    {
        this.liters = liters;
    }

    public Integer getCost()
    {
        return cost;
    }

    public void setCost(Integer cost)
    {
        this.cost = cost;
    }

    public boolean isFullTankup()
    {
        return fullTankup;
    }

    public void setFullTankup(boolean fullTankup)
    {
        this.fullTankup = fullTankup;
    }
}
