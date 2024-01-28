package pack.sor.carnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Date;

import pack.sor.carnote.model.AutoData;
import pack.sor.carnote.model.AutoData;
import pack.sor.carnote.model.AutoData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Okno dodania nowego auta
 */

public class NewCarActivity extends AppCompatActivity {

    public static final String AUTO_DATA_NEW_CAR = "AUTO_DATA_NEW_CAR";
    public static final String IS_NEW_CAR_MASTER_CAR = "IS_NEW_CAR_MASTER_CAR";

    private EditText makeEditText;
    private EditText modelEditText;
    private EditText colorEditText;
    private Switch isMasterCarSwitch;
    private Button confirmButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_car_layout);
        setTitle(getResources().getString(R.string.new_car));

        makeEditText = (EditText) findViewById(R.id.make_edittext);
        modelEditText = (EditText) findViewById(R.id.model_edittext);
        colorEditText = (EditText) findViewById(R.id.color_edittext);
        colorEditText = (EditText) findViewById(R.id.color_edittext);

        isMasterCarSwitch = (Switch) findViewById(R.id.master_car_switch);

        confirmButton = (Button) findViewById(R.id.confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoData autoData = new AutoData(modelEditText.getText().toString(), makeEditText.getText().toString(), colorEditText.getText().toString());
                Boolean isMasterCar = isMasterCarSwitch.isChecked();
                Intent intent = new Intent();
                intent.putExtra(AUTO_DATA_NEW_CAR, autoData);
                intent.putExtra(IS_NEW_CAR_MASTER_CAR, isMasterCar);
                setResult(Activity.RESULT_OK, intent);
                postData(modelEditText.getText().toString(), makeEditText.getText().toString(), colorEditText.getText().toString());
                finish();
            }
        });
    }

    private void postData(String model, String make, String color) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.106:8080/api/Car/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        AutoData modal = new AutoData(model, make, color);
        Call<AutoData> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<AutoData>() {
            @Override
            public void onResponse(Call<AutoData> call, Response<AutoData> response) {
                AutoData responseFromAPI = response.body();
            }
            @Override
            public void onFailure(Call<AutoData> call, Throwable t) {
            }
        });
    }
}
