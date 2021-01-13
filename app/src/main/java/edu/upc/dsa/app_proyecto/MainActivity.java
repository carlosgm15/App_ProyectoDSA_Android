package edu.upc.dsa.app_proyecto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Models.Objetos;
import Models.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button btn;
    //crear retrofit
    Retrofit retrofit;
    //para la interfaz
    UserService userService;

    User usuario;
    private ProgressBar loginbar;

    List<Objetos> objetosList;

    public TextView editTextName, editTextPassword ;
    public String nameUser,passwordUser;
    private static int REGISTRAR = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbar = findViewById(R.id.loginBar);
        btn = (Button) findViewById(R.id.btnDash);;
        loginbar.setVisibility(View.INVISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbar.setVisibility(View.VISIBLE);
                Espera(2000);
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });


        editTextName = this.findViewById(R.id.name);
        editTextPassword = this.findViewById(R.id.passwodLogin);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
               .baseUrl("http://147.83.7.204:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        userService = retrofit.create(UserService.class);
    }

    public void Siguiente(View view) {
        loginbar.setVisibility(View.VISIBLE);
        Espera(2000);
        Intent siguiente = new Intent(this, MainActivity2.class);
        siguiente.putExtra("nombre", "");
        startActivityForResult(siguiente, REGISTRAR);
    }
    //Notifica mensajes
    private void NotifyUser(String MSG){
        loginbar.setVisibility(View.VISIBLE);
        Espera(4000);
        Toast toast = Toast.makeText(MainActivity.this,MSG,Toast.LENGTH_SHORT);
    }
    public void onButtonLoginClick(View view) {
        //Retrofit Implementation on Button Press
        //Adding Interceptor
        nameUser = editTextName.getText().toString();
        passwordUser = editTextPassword.getText().toString();
        User usuariotmp = new User();
        //
        usuariotmp.setUsername(nameUser);
        usuariotmp.setPassword(passwordUser);
        try {
            Call<User> usersCall = userService.logginUser(usuariotmp);
            /* Android Doesn't allow synchronous execution of Http Request and so we must put it in queue*/
            usersCall.enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 201) {
                        loginbar.setVisibility(View.VISIBLE);
                        Espera(4000);
                        NotifyUser("Successful");
                        MainActivity.this.usuario = response.body();
                        NotifyUser("Usuario" +usuario);
                        objetosList = usuario.objetosList;
                        NotifyUser("objetos" + objetosList);
                        Log.d("MYAPP", "La lista de objetos es"+objetosList);
                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("name", nameUser);
                        startActivity(intent);
                        finish();
                    }
                    loginbar.setVisibility(View.VISIBLE);
                    Espera(2000);
                    if (response.code() == 409) {NotifyUser("User Duplicado , Inserta de nuevo");}
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    NotifyUser("Error Server");
                }

            });
        } catch (Exception e) {
            loginbar.setVisibility(View.VISIBLE);
            Espera(4000);
            NotifyUser("Exception: " + e.toString());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                loginbar.setVisibility(View.VISIBLE);
                Espera(4000);
                User usuariotmp = new User();
                usuariotmp.setUsername(nameUser);
                usuariotmp.setPassword(passwordUser);
                postAddUser(usuariotmp);
            }

        }
    }

    private void SaveLoginSharedPreference(String nameUser, String passwordUser) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nameUser", nameUser);
        editor.putString("passwordUser", passwordUser);
        editor.apply();
    }
    public void Espera (int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                loginbar.setVisibility(View.INVISIBLE);
            }
        }, milisegundos);
    }

    public void postAddUser (User usuariotmp){
        try {
            Call<User> usersCall = userService.addUser(usuariotmp);
            /* Android Doesn't allow synchronous execution of Http Request and so we must put it in queue*/
            usersCall.enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 201) {
                        loginbar.setVisibility(View.VISIBLE);
                        Espera(4000);
                        NotifyUser("Successful");
                        MainActivity.this.usuario = response.body();
                        NotifyUser("Usuario" +usuario);
                        objetosList = usuario.objetosList;
                        NotifyUser("objetos" + objetosList);
                        Log.d("MYAPP", "La lista de objetos es"+objetosList);
                        //Lanzar una nueva actividad con otra pantalla
                    }
                    if (response.code() == 409) {NotifyUser("User Duplicado , Inserta de nuevo");}
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    NotifyUser("Error Server");
                }

            });
        } catch (Exception e) {
            loginbar.setVisibility(View.VISIBLE);
            Espera(4000);
            NotifyUser("Exception: " + e.toString());
        }
    }




}