package pack.sor.carnote;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import pack.sor.carnote.model.AutoData;
import pack.sor.carnote.model.RepairRecord;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Okno formularza nowego tankowania
 */

public class NewRepairActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    public static final String NEW_REPAIR_RECORD = "NEW_REPAIR_RECORD";
    public static final String AUTO_DATA_OBJ = "AUTO_DATA_OBJ";
    private EditText dateEditText;
    private EditText mileageEditText;
    private TextView partsEditTextLabel;
    private EditText partsEditText;
    private TextView costEditTextLabel;
    private EditText costEditText;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private Button confirmButton;
    private AutoData autoData;
    private DateFormat dateFormat;
    private TextView mileageEditTextLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_repair_layout);
        obtainExtras();
        if (savedInstanceState != null)
        {
            autoData = (AutoData) savedInstanceState.get(AUTO_DATA_OBJ);
        }
        viewInit();
        setTitle(getResources().getString(R.string.new_repair));
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
        partsEditText = (EditText) findViewById(R.id.part);
        partsEditTextLabel = (TextView) findViewById(R.id.part_label);
        costEditText = (EditText) findViewById(R.id.cost);
        costEditTextLabel = (TextView) findViewById(R.id.cost_label);
        confirmButton = (Button) findViewById(R.id.confirm);

        dateEditText.setText(getCurrentDate());

        dateEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewRepairActivity.this, NewRepairActivity.this, year, month, day);
                datePickerDialog.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (validateMileage() && validateCost())
                {
                    RepairRecord repair = new RepairRecord(getDateEditTextDate(), getMileageInteger(), partsEditText.getText().toString(), getCostInteger());
                    Intent intent = new Intent();
                    intent.putExtra(NEW_REPAIR_RECORD, repair);
                    postData(dateEditText.getText().toString(), getMileageInteger(), partsEditText.getText().toString(), getCostInteger());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
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
            costEditTextLabel.setText(getResources().getString(R.string.repair_cost));
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
        if (Integer.valueOf(mileageEditText.getText().toString()) <= 0)
        {
            mileageEditTextLabel.setText(R.string.mileage_validation_positive_message);
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
            } else
            {
                mileageEditTextLabel.setText(getResources().getString(R.string.mileage));
                mileageEditTextLabel.setTextColor(getResources().getColor(R.color.black));
                return true;
            }
        }
        return true;
    }

    private Date getDateEditTextDate(){
        try
        {
            return formatter.parse(dateEditText.getText().toString());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        //dateFormat = DateFormat.getDateInstance();
        return new Date();
    }

    private Integer getCostInteger()
    {
        return Integer.valueOf(costEditText.getText().toString());
    }

    private Integer getMileageInteger()
    {
        return Integer.valueOf(mileageEditText.getText().toString());
    }

    private String getCurrentDate()
    {
//        dateFormat = DateFormat.getDateInstance();
//        Date date = new Date();
//        return dateFormat.format(date);

        Date date = new Date();
        return formatter.format(date).toString();
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
        dateEditText.setText(formatter.format(calendar.getTime()));
    }

    private void postData(String repairDate, Integer mileage, String parts, Integer cost) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.106:8080/api/Repair/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String requiredDate = df.format(new Date()).toString();
        RepairRecord modal = new RepairRecord(repairDate, mileage, parts, cost);

        Call<RepairRecord> call = retrofitAPI.createPost(modal);

        call.enqueue(new Callback<RepairRecord>() {
            @Override
            public void onResponse(Call<RepairRecord> call, Response<RepairRecord> response) {
                RepairRecord responseFromAPI = response.body();
            }

            @Override
            public void onFailure(Call<RepairRecord> call, Throwable t) {
            }
        });
    }
}

