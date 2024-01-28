package pack.sor.carnote.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pack.sor.carnote.model.AutoData;
import pack.sor.carnote.model.TankUpRecord;

/**
 * Klasa licząca statystyki dla wybranego auta
 */

public class Calculator
{
    /**
     * Dane auta dla którego wyliczamy
     */
    private AutoData autoData;
    /**
     * Lista statystyk wyliczonych pomiędzy dwoma tankowaniami do pełna
     */
    private List<CountableTank> countableTankList;

    public Calculator(AutoData autoData)
    {
        this.autoData = autoData;
        initStatistics();
    }

    private void initStatistics()
    {
        countableTankList = new ArrayList<>();

        //sortuję na podstawie daty
        Collections.sort(autoData.getTankUpRecords(), (o1, o2) -> o1.getDate().getTime() < o2.getDate().getTime() ? -1 : 1);

        Iterator<TankUpRecord> iterator = autoData.getTankUpRecords().iterator();
        boolean notFirstFullTankup = false;
        int tempCost = 0;
        int literSum = 0;
        TankUpRecord lastTankupRecord = null;
        //iteruję po wszystkich tankowaniach i liczę koszt, czas i przebieg pomiędzy tankowaniami pełnymi
        while (iterator.hasNext())
        {
            TankUpRecord current = iterator.next();
            if (current.isFullTankup())
            {
                if (lastTankupRecord != null)
                {
                    long timeDiff = current.getDate().getTime() - lastTankupRecord.getDate().getTime();
                    int cost = tempCost + current.getCost();
                    int mileageDiff = current.getMileage() - lastTankupRecord.getMileage();
                    int liters = literSum + current.getLiters();

                    countableTankList.add(new CountableTank(timeDiff, cost, mileageDiff, liters));
                }

                tempCost = 0;
                literSum = 0;
                lastTankupRecord = current;
            } else
            {
                tempCost += current.getCost();
                literSum += current.getLiters();
            }
        }

    }

    /**
     * @return Koszt paliwa zł/dzień
     */
    public String getOneDayCost()
    {
        float plnUseForDay = plnUsePerDay();
        if (plnUseForDay != Float.NaN && plnUseForDay != Float.POSITIVE_INFINITY)
        {
            return String.format("%.2f zł / dzień", plnUseForDay);
        } else
        {
            return "-";
        }
    }

    float plnUsePerDay()
    {
        float costDiffSum = 0;
        long timeDiffSum = 0;
        for (CountableTank countableTank : countableTankList)
        {
            costDiffSum += countableTank.cost;
            timeDiffSum += countableTank.timeDiff;
        }
        float dayDiff = TimeUnit.MILLISECONDS.toDays(timeDiffSum);
        return costDiffSum / dayDiff;
    }


    /**
     * @return Spalanie na 100 km
     */
    public String getFuelConsumption()
    {
        float fuelUseInLitersOn100Km = getFuelUseInLitersOn100Km();
        if (fuelUseInLitersOn100Km != Float.NaN)
        {
            return String.format("%.2f L / 100 km", fuelUseInLitersOn100Km);
        } else
        {
            return "-";
        }
    }

    float getFuelUseInLitersOn100Km()
    {
        float mileageDiffSum = 0;
        float litersDiffSum = 0;
        for (CountableTank countableTank : countableTankList)
        {
            mileageDiffSum += countableTank.mileageDiff;
            litersDiffSum += countableTank.liters;
        }
        return 100 * litersDiffSum / mileageDiffSum;
    }

    /**
     * @return Ile kilometrów przejedziesz na litrze
     */
    public String getHowLongLiter()
    {
        float kmOnOneLiter = kmOnOneLiter();
        if (kmOnOneLiter != Float.NaN)
        {
            return String.format("%.2f km na 1 L", kmOnOneLiter);
        } else
        {
            return "-";
        }
    }

    float kmOnOneLiter()
    {
        float mileageDiffSum = 0;
        float litersDiffSum = 0;
        for (CountableTank countableTank : countableTankList)
        {
            mileageDiffSum += countableTank.mileageDiff;
            litersDiffSum += countableTank.liters;
        }
        return mileageDiffSum / litersDiffSum;
    }

    /**
     * @return Koszt przejechania jednego kilometra
     */
    public String getKmCost()
    {
        float kmCost = getHowMuchPlnForOneKm();
        if (kmCost != Float.NaN)
        {
            return String.format("%.2f zł za km", kmCost);
        } else
        {
            return "-";
        }
    }

    private float getHowMuchPlnForOneKm()
    {
        float costSum = 0;
        float mileageDiff = 0;
        for (CountableTank countableTank : countableTankList)
        {
            costSum += countableTank.cost;
            mileageDiff += countableTank.mileageDiff;
        }
        return costSum / mileageDiff;

    }

    /**
     * @return Średnia cena paliwa
     */
    public String getAverageFuel()
    {
        float literCost = getHowMuchPlnForOneL();
        if (literCost != Float.NaN)
        {
            return String.format("%.2f zł za l", literCost);
        } else
        {
            return "-";
        }
    }

    private float getHowMuchPlnForOneL()
    {
        float costSum = 0;
        float litersDiffSum = 0;
        for (CountableTank countableTank : countableTankList)
        {
            costSum += countableTank.cost;
            litersDiffSum += countableTank.liters;
        }
        return costSum / litersDiffSum;

    }

    /**
     * @return Ilość kilometrów bez tankowania
     */
    public String getLongestWithoutTankup()
    {
        float kmForTank = getHowMuchKmForTank();
        if (kmForTank != Float.NaN)
        {
            return String.format("%.2f km na baku", kmForTank);
        } else
        {
            return "-";
        }
    }

    private Integer getHowMuchKmForTank()
    {
        return Collections.max(countableTankList, (o1, o2) -> o1.mileageDiff < o2.mileageDiff ? -1 : 1).mileageDiff;
    }

    /**
     * @return Częstość tankowań
     */
    public String getTankupFrequency()
    {
        float daysBetweenTankup = howOftenTankupInDays();
        if (daysBetweenTankup != Float.NaN)
        {
            return String.format("Co %.2f dni", daysBetweenTankup);
        } else
        {
            return "-";
        }
    }

    private float howOftenTankupInDays()
    {
        long daysSum = 0;
        TankUpRecord previous;
        //tankupRecords posortowane datą z init
        if (autoData.getTankUpRecords().size() > 0)
        {
            int lastIndex = autoData.getTankUpRecords().size() - 1;
            long newRecord = autoData.getTankUpRecords().get(lastIndex).getDate().getTime();
            long firstRecord = autoData.getTankUpRecords().get(0).getDate().getTime();
            long timeDiff = newRecord - firstRecord;
            daysSum = TimeUnit.MILLISECONDS.toDays(timeDiff)+1;
        }

        return (float)daysSum / autoData.getTankUpRecords().size();
    }

    public String getFuelOneDay()
    {
        return "-";
    }

    public String getAverageRepair()
    {
        return "-";
    }

    public String getRepairsTotal()
    {
        return "-";
    }

    public String getRepairFrequency()
    {
        return "-";
    }

    public String getRepairCostMonthly()
    {
        return "-";
    }

    /**
     * Model statystyki wyliczonej pomiędzy dwoma tankowaniami do pełna
     */
    private class CountableTank
    {
        private long timeDiff;
        private int cost;
        private int mileageDiff;
        private int liters;

        public CountableTank(long timeDiff, int cost, int mileageDiff, int liters)
        {
            this.timeDiff = timeDiff;
            this.cost = cost;
            this.mileageDiff = mileageDiff;
            this.liters = liters;
        }
    }
}
