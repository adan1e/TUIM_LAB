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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pack.sor.carnote.R;
import pack.sor.carnote.RetrofitAPI;
import pack.sor.carnote.historylist.HistoryRemover;
import pack.sor.carnote.model.AutoData;
import pack.sor.carnote.model.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText loginTextEdit;
    TextView link;
    EditText passwordTextEdit;
    Button buttonLogin;

    private static final int USER_REQUEST_CODE = 666;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        loginTextEdit = (EditText) findViewById(R.id.login_login);
        passwordTextEdit = (EditText) findViewById(R.id.password_login);
        link = (TextView) findViewById(R.id.register_href);
        buttonLogin = (Button) findViewById(R.id.btn_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login, password;
                login = String.valueOf(loginTextEdit.getText());
                password = String.valueOf(passwordTextEdit.getText());

                if (TextUtils.isEmpty(login)){
                    Toast.makeText(LoginActivity.this, "Wprowadz login", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Wprowadz haslo", Toast.LENGTH_SHORT).show();
                    return;
                }

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.0.106:8080/api/User/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

                Call<List<UserData>> call = retrofitAPI.getUsers();

                call.enqueue(new Callback<List<UserData>>() {
                    @Override
                    public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response) {
                        List<UserData> allUsers = response.body();
                        assert allUsers != null;
                        for (UserData userData : allUsers){
                            if(userData.getLogin().equals(login) && userData.getPassword().equals(password)){
                                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                        // Tutaj możesz przetwarzać pobrane dane
                        Toast.makeText(LoginActivity.this, "Nieprawidlowy login lub haslo", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<List<UserData>> call, Throwable t) {
                        // Obsługa błędu
                    }
                });

            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

    }
}
