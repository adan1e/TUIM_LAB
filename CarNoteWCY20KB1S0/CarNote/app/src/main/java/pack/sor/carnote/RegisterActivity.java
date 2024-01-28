package pack.sor.carnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pack.sor.carnote.model.TankUpRecord;
import pack.sor.carnote.model.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    public static final String NEW_USER = "NEW_USER";
    EditText loginTextEdit;
    TextView link;
    EditText passwordTextEdit;
    Button buttonRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        loginTextEdit = (EditText) findViewById(R.id.login_register);
        passwordTextEdit = (EditText) findViewById(R.id.password_register);
        link = (TextView) findViewById(R.id.login_href);
        buttonRegister = (Button) findViewById(R.id.btn_register);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login, password;
                login = String.valueOf(loginTextEdit.getText());
                password = String.valueOf(passwordTextEdit.getText());

                if (TextUtils.isEmpty(login)){
                    Toast.makeText(RegisterActivity.this, "Wprowadz login", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "Wprowadz haslo", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserData user = new UserData(login, password);
                Intent intent = new Intent();
                intent.putExtra(NEW_USER, user);
                postData(login, password);
                setResult(Activity.RESULT_OK, intent);
                Toast.makeText(RegisterActivity.this, "Rejestracja powiodla sie!", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void postData(String login, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.106:8080/api/User/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        UserData modal = new UserData(login, password);
        Call<UserData> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                UserData responseFromAPI = response.body();
            }
            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
            }
        });
    }
}
