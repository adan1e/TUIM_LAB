package pack.sor.carnote.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import pack.sor.carnote.CustomerDateAndTimeDeserialize;
import pack.sor.carnote.R;

/**
 * Model danych pojedynczegj naprawy
 */
public class RepairRecord implements Serializable, ViewHolderAdaptable, CostCountable
{
    /**
     * Data wykonania tankowania
     */
//    @JsonProperty("repairDate")
//    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "MMM dd, yyyy HH:mm:ss aa")
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
//    @JsonDeserialize(using= CustomerDateAndTimeDeserialize.class)
    private Date repairDate;

    @JsonProperty("repairDateString")
    private String repairDateString;

    /**
     * Przebieg auta w momencie tankowania
     */
    @JsonProperty("mileage")
    private Integer mileage;

    /**
     * Co było naprawiane
     */
    @JsonProperty("parts")
    private String parts;

    /**
     * Koszt naprawy
     */
    @JsonProperty("cost")
    private Integer cost;

    public RepairRecord(Date repairDate, Integer mileage, String parts, Integer cost)
    {
        this.repairDate = repairDate;
        this.mileage = mileage;
        this.parts = parts;
        this.cost = cost;
    }

    public RepairRecord(String repairDateString, Integer mileage, String parts, Integer cost) {
        this.repairDateString = repairDateString;
        this.mileage = mileage;
        this.parts = parts;
        this.cost = cost;
    }

    @Override
    public Date getDate()
    {
        return repairDate;
    }

    public Integer getMileage()
    {
        return mileage;
    }

    @Override
    public String getFirstText()
    {
        return parts;
    }

    @Override
    public String getSecondText()
    {
        Currency currency = Currency.getInstance(Locale.getDefault());
        return String.valueOf(cost) + " " + currency.getCurrencyCode();
    }

    @Override
    public Integer getCategoryDrawable()
    {
        return R.drawable.ic_repair;
    }

    public Date getRepairDate()
    {
        return repairDate;
    }

    public void setRepairDate(Date repairDate)
    {
        this.repairDate = repairDate;
    }

    public void setMileage(Integer mileage)
    {
        this.mileage = mileage;
    }

    public String getParts()
    {
        return parts;
    }

    public void setParts(String parts)
    {
        this.parts = parts;
    }

    public Integer getCost()
    {
        return cost;
    }

    public void setCost(Integer cost)
    {
        this.cost = cost;
    }
}
