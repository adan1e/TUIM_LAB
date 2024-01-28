package pack.sor.carnote;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import pack.sor.carnote.model.AutoData;
import pack.sor.carnote.statistics.Calculator;

/**
 * Okno podsumowania auta z jego statystykami
 */

public class CarInfoActivity extends AppCompatActivity
{
    private Calculator calculator;
    private AutoData autoData;
    private TextView repairCostMonthly;
    private TextView make;
    private TextView model;
    private TextView color;
    private TextView toOneHundred;
    private TextView fuelConsumption;
    private TextView oneDayCost;
    private TextView howLongLiter;
    private TextView kmCost;
    private TextView averageFuel;
    private TextView longestWithoutTankup;
    private TextView tankupFrequency;
    private TextView fuelOneDay;
    private TextView averageRepair;
    private TextView repairsTotal;
    private TextView repairFrequency;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        obtainExtras();
        setContentView(R.layout.car_info_layout);
        setTitle(getResources().getString(R.string.car_info_title));

        initViews();
        calculate();
    }

    private void initViews()
    {
        make = (TextView) findViewById(R.id.make);
        model = (TextView) findViewById(R.id.model);
        color = (TextView) findViewById(R.id.color);
        toOneHundred = (TextView) findViewById(R.id.to_one_hundred);
        fuelConsumption = (TextView) findViewById(R.id.fuel_consumption);
        oneDayCost = (TextView) findViewById(R.id.one_day_cost);
        howLongLiter = (TextView) findViewById(R.id.how_long_liter);
        kmCost = (TextView) findViewById(R.id.km_cost);
        averageFuel = (TextView) findViewById(R.id.average_fuel);
        longestWithoutTankup = (TextView) findViewById(R.id.longest_without_tankup);
        tankupFrequency = (TextView) findViewById(R.id.tankup_frequency);
        fuelOneDay = (TextView) findViewById(R.id.fuel_one_day_cost);
        averageRepair = (TextView) findViewById(R.id.average_repair);
        repairsTotal = (TextView) findViewById(R.id.repairs_total_cost);
        repairFrequency = (TextView) findViewById(R.id.repair_frequency);
        repairCostMonthly = (TextView) findViewById(R.id.repair_cost_monthly);
    }

    private void calculate()
    {
        calculator = new Calculator(autoData);
        make.setText(autoData.getMake());
        model.setText(autoData.getModel());
        color.setText(autoData.getColor());
        toOneHundred.setText(String.format("%s sec", autoData.getBestSpeed().toString()));

        fuelConsumption.setText(calculator.getFuelConsumption());
        oneDayCost.setText(calculator.getOneDayCost());
        howLongLiter.setText(calculator.getHowLongLiter());
        kmCost.setText(calculator.getKmCost());

        averageFuel.setText(calculator.getAverageFuel());
        longestWithoutTankup.setText(calculator.getLongestWithoutTankup());
        tankupFrequency.setText(calculator.getTankupFrequency());
        fuelOneDay.setText(calculator.getFuelOneDay());

        averageRepair.setText(calculator.getAverageRepair());
        repairsTotal.setText(calculator.getRepairsTotal());
        repairFrequency.setText(calculator.getRepairFrequency());
        repairCostMonthly.setText(calculator.getRepairCostMonthly());

    }

    private void obtainExtras()
    {
        autoData = (AutoData) getIntent().getExtras().getSerializable(MenuActivity.SPECIAL_DATA);
    }
}

