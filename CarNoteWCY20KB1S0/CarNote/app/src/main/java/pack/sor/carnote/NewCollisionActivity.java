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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import pack.sor.carnote.model.AutoData;
import pack.sor.carnote.model.CollisionRecord;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Okno formularza nowej stłuczki
 */

public class NewCollisionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    public static final String NEW_COLLISION_TAG = "NEW_COLLISION_TAG";
    public static final String AUTO_DATA_OBJ = "AUTO_DATA_OBJ";
    private EditText dateEditText;
    private EditText mileageEditText;
    private TextView platesEditTextLabel;
    private EditText platesEditText;
    private TextView witnessEditTextLabel;
    private EditText witnessEditText;

    private Button confirmButton;
    private AutoData autoData;
    private DateFormat dateFormat;
    private TextView mileageEditTextLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_collision_layout);
        obtainExtras();
        if (savedInstanceState != null)
        {
            autoData = (AutoData) savedInstanceState.get(AUTO_DATA_OBJ);
        }
        viewInit();
        setTitle(getResources().getString(R.string.new_collision));
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
        platesEditText = (EditText) findViewById(R.id.plates);
        platesEditTextLabel = (TextView) findViewById(R.id.plates_label);
        witnessEditText = (EditText) findViewById(R.id.witness);
        witnessEditTextLabel = (TextView) findViewById(R.id.cost_label);
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(NewCollisionActivity.this, NewCollisionActivity.this, year, month, day);

                datePickerDialog.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (validateMileage())
                {
                    CollisionRecord collision = new CollisionRecord(getDateEditTextDate(), getMileageInteger(), platesEditText.getText().toString(), witnessEditText.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra(NEW_COLLISION_TAG, collision);
                    postData(getDateEditTextDate().toString(), getMileageInteger(), platesEditText.getText().toString(), witnessEditText.getText().toString());
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
    }


    private boolean validateMileage()
    {
        //todo wprowadzanie z data wstecz doimplementować!
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

    private void postData(String date, Integer mileage, String plates, String witness) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.106:8080/api/Collision/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        CollisionRecord modal = new CollisionRecord(date,mileage,plates,witness);
        Call<CollisionRecord> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<CollisionRecord>() {
            @Override
            public void onResponse(Call<CollisionRecord> call, Response<CollisionRecord> response) {
                CollisionRecord responseFromAPI = response.body();
            }
            @Override
            public void onFailure(Call<CollisionRecord> call, Throwable t) {
            }
        });
    }
}
