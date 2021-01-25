package edu.upc.dsa.app_proyecto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

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
    //crear retrofit
    Retrofit retrofit;
    //para la interfaz
    UserService userService;

    User usuario;
    private ProgressBar loginbar;

    List<Objetos> objetosList;

    public TextView editTextName, editTextPassword ;
    public String nameUser,passwordUser, id, passUser2, nameUser2;
    private static int REGISTRAR = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbar = findViewById(R.id.loginBar);
        editTextName = this.findViewById(R.id.usernameLogin);
        editTextPassword = this.findViewById(R.id.passwodLogin);
        CargarPre();

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
        Log.i("marc", "Fent el logging");
        Log.i("marc", ""+editTextName);
        Log.i("marc", ""+editTextPassword);
        nameUser = editTextName.getText().toString();
        passwordUser = editTextPassword.getText().toString();
        if (nameUser == null || passwordUser == null)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Campos vacios", Toast.LENGTH_SHORT);
            toast.show();

        }
        else {

            User usuariotmp = new User();
            //
            usuariotmp.setUsername(nameUser);
            usuariotmp.setPassword(passwordUser);
            try {
                Call<User> usersCall = userService.logginUser(usuariotmp);
                // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
                usersCall.enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.i("marc", ""+response.code());
                        if (response.code() == 201) {
                            loginbar.setVisibility(View.VISIBLE);
                            Espera(4000);
                            NotifyUser("Successful");
                            MainActivity.this.usuario = response.body();
                            NotifyUser("Usuario" + usuario);
                            objetosList = usuario.objetosList;
                            id= usuario.getId();
                            NotifyUser("objetos" + objetosList);
                            Log.d("MYAPP", "La lista de objetos es" + objetosList);
                            EscribirPre();
                            Intent intent = new Intent(MainActivity.this, Dashboard.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("name", nameUser);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            if(response.code() == 404){
                                NotifyUser("Contraseña incorrecta");
                            }
                            //finish();
                        }
                        loginbar.setVisibility(View.VISIBLE);
                        Espera(2000);
                        if (response.code() == 409) {
                            NotifyUser("User Duplicado , Inserta de nuevo");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("marc", "on failure",t);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle bundle2 = data.getExtras();
                nameUser2 = bundle2.getString("username2");
                passUser2 = bundle2.getString("password2");
                loginbar.setVisibility(View.VISIBLE);
                Espera(4000);
                User usuariotmp = new User();
                usuariotmp.setUsername(nameUser2);
                usuariotmp.setPassword(passUser2);
                usuariotmp.setDinero(100);
                postAddUser(usuariotmp);
            }

        }
    }
    private void CargarPre () {
        SharedPreferences sharedPref = getSharedPreferences("credenciales", MODE_PRIVATE);
        nameUser = sharedPref.getString("nameUser","username");
        passwordUser = sharedPref.getString("pass","password");
        editTextName.setText(nameUser);
        editTextPassword.setText(passwordUser);

    }
    private void EscribirPre() {
        SharedPreferences sharedPref = getSharedPreferences("credenciales", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString( "nameUser", nameUser);
        editor.putString("pass", passwordUser);
        editor.apply();

    }

    /*private void SaveLoginSharedPreference(String nameUser, String passwordUser) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nameUser", nameUser);
        editor.putString("passwordUser", passwordUser);
        editor.apply();
    }*/
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
            Call<User> usersCall2 = userService.adduser(usuariotmp);
            /* Android Doesn't allow synchronous execution of Http Request and so we must put it in queue*/
            usersCall2.enqueue(new Callback<User>() {

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