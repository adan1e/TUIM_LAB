package pack.sor.carnote;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import  pack.sor.carnote.model.AutoData;
import  pack.sor.carnote.model.TankUpRecord;
import  pack.sor.carnote.model.TankUpRecord;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Okno formularza nowego tankowania
 */

public class NewTankupActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    public static final String NEW_TANKUP_RECORD = "NEW_TANKUP_RECORD";
    public static final String AUTO_DATA_OBJ = "AUTO_DATA_OBJ";
    private EditText dateEditText;
    private EditText mileageEditText;
    private TextView litersEditTextLabel;
    private EditText litersEditText;
    private TextView costEditTextLabel;
    private TextView mileageEditTextLabel;
    private EditText costEditText;
    private Switch fullTankupSwitch;
    private TextView promptFullTankupText;
    private Button confirmButton;

    private AutoData autoData;
    private DateFormat dateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_tank_up_layout);
        obtainExtras();
        if (savedInstanceState != null)
        {
            autoData = (AutoData) savedInstanceState.get(AUTO_DATA_OBJ);
        }
        viewInit();
        setTitle(getResources().getString(R.string.new_tankup));
    }

    private void obtainExtras()
    {
        autoData = (AutoData) getIntent().getExtras().getSerializable(MenuActivity.SPECIAL_DATA);
    }

    private void viewInit()
    {
        dateEditText = (EditText) findViewById(R.id.date);
        mileageEditText = (EditText) findViewById(R.id.mileage);
        mileageEditTextLabel = (TextView) findViewById(R.id.mileage_label);
        litersEditText = (EditText) findViewById(R.id.liters);
        litersEditTextLabel = (TextView) findViewById(R.id.liters_label);
        costEditText = (EditText) findViewById(R.id.cost);
        costEditTextLabel = (TextView) findViewById(R.id.cost_label);
        fullTankupSwitch = (Switch) findViewById(R.id.full_tankup_switch);
        promptFullTankupText = (TextView) findViewById(R.id.full_tank_prompt);
        confirmButton = (Button) findViewById(R.id.confirm);

        dateEditText.setText(getCurrentDate());

        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (validateMileage() && validateCost() && validateLiters())
                {
                    TankUpRecord tank = new TankUpRecord(getDateEditTextDate(), getMileageInteger(), getLitersInteger(), getCostInteger(), fullTankupSwitch.isChecked());
                    Intent intent = new Intent();
                    intent.putExtra(NEW_TANKUP_RECORD, tank);
                    postData(getDateEditTextDate().toString(), getMileageInteger(), getLitersInteger(), getCostInteger());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
        dateEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewTankupActivity.this, NewTankupActivity.this, year, month, day);

                datePickerDialog.show();
            }
        });


        mileageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                //wyjście z kontrolki
                if (!hasFocus)
                {
                    validateMileage();
                }
            }
        });

        costEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                //wyjście z kontrolki
                if (!hasFocus)
                {
                    validateCost();
                }
            }
        });

        litersEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                //wyjście z kontrolki
                if (!hasFocus)
                {
                    validateLiters();
                }
            }
        });

        fullTankupSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    promptFullTankupText.setVisibility(View.GONE);
                } else
                {
                    promptFullTankupText.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean validateLiters()
    {
        if (TextUtils.isEmpty(litersEditText.getText().toString()))
        {
            litersEditTextLabel.setText(R.string.liter_validation_notempty_message);
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        } else if (Integer.valueOf(litersEditText.getText().toString()) <= 0)
        {
            litersEditTextLabel.setText(R.string.liter_validation_positive_message);
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        } else
        {
            litersEditTextLabel.setText(getResources().getString(R.string.added_liters));
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
    }

    private boolean validateCost()
    {
        if (TextUtils.isEmpty(costEditText.getText().toString()))
        {
            costEditTextLabel.setText(R.string.cost_validation_notempty_message);
            costEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        } else if (Integer.valueOf(costEditText.getText().toString()) <= 0)
        {
            costEditTextLabel.setText(R.string.cost_validation_positive_message);
            costEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        } else
        {
            costEditTextLabel.setText(getResources().getString(R.string.tank_cost));
            costEditTextLabel.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
    }

    private boolean validateMileage()
    {
        if (TextUtils.isEmpty(mileageEditText.getText().toString()))
        {
            mileageEditTextLabel.setText(R.string.mileage_validation_notempty_message);
            mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
        int size = autoData.getTankUpRecords().size();
        if (autoData.getTankUpRecords().size() != 0)
        {
            Integer newMileage = Integer.valueOf(mileageEditText.getText().toString());
            Integer oldMileage = autoData.getTankUpRecords().get(size - 1).getMileage();
            if (newMileage <= oldMileage)
            {
                mileageEditTextLabel.setText(R.string.mileage_validation_notlesser_message);
                mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red));
                return false;
            }
        }
        mileageEditTextLabel.setText(getResources().getString(R.string.mileage));
        mileageEditTextLabel.setTextColor(getResources().getColor(R.color.black));
        return true;
    }

    private Date getDateEditTextDate()
    {
        try
        {
            return dateFormat.parse(dateEditText.getText().toString());
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        dateFormat = DateFormat.getDateInstance();
        return new Date();
    }

    private Integer getCostInteger()
    {
        return Integer.valueOf(costEditText.getText().toString());
    }

    private Integer getLitersInteger()
    {
        return Integer.valueOf(litersEditText.getText().toString());
    }

    private Integer getMileageInteger()
    {
        return Integer.valueOf(mileageEditText.getText().toString());
    }

    private String getCurrentDate()
    {
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable(AUTO_DATA_OBJ, autoData);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        dateEditText.setText(dateFormat.format(calendar.getTime()));
    }

    private void postData(String date, Integer mileage, Integer liters, Integer cost) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.106:8080/api/TankUp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        TankUpRecord modal = new TankUpRecord(date,mileage,liters, cost);
        Call<TankUpRecord> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<TankUpRecord>() {
            @Override
            public void onResponse(Call<TankUpRecord> call, Response<TankUpRecord> response) {
                TankUpRecord responseFromAPI = response.body();
            }
            @Override
            public void onFailure(Call<TankUpRecord> call, Throwable t) {
            }
        });
    }
}
