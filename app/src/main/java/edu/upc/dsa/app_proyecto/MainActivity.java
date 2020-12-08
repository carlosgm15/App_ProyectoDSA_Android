package edu.upc.dsa.app_proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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

    //Para crear el recycler
    private RecyclerView recyclerView;
    //  private RecyclerView.Adapter mAdapter;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //para la interfaz
    UserService userService;

    User usuario;
    List<Objetos> objetosList;

    public TextView editTextName, editTextPassword ;
    public String nameUser,passwordUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Esto es configuracion del recycler
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        editTextName = this.findViewById(R.id.editTextName);
        editTextPassword = this.findViewById(R.id.editTextPassword);


        //Configuracion del retrofit
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
//                .baseUrl("http://147.83.7.204:8080/dsaApp/")
                // .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        userService = retrofit.create(UserService.class);
    }

    public void Siguiente(View view) {
        Intent siguiente = new Intent(this, MainActivity2.class);
        startActivity(siguiente);
        //finish();
    }

    public void btnLogin_Click(View view) {
        final EditText usernameR = (EditText) findViewById(R.id.usernameLogin);
        final EditText passwordR = (EditText) findViewById(R.id.passwodLogin);
        String usuario = usernameR.getText().toString();
        String password = passwordR.getText().toString();

        if (usuario.isEmpty() && password.isEmpty())
            Toast.makeText(MainActivity.this,"Error: Inserte un usuario y una contraseña",Toast.LENGTH_LONG).show();
        else if (usuario.contentEquals("jose") && password.contentEquals("123"))
            Toast.makeText(MainActivity.this,"Loggin realizado: Usuario y contraseña correcta",Toast.LENGTH_LONG).show();
        else if (usuario.contentEquals("jose") && password.contentEquals("123")==false)
            Toast.makeText(MainActivity.this,"Error: Contraseña incorrecta",Toast.LENGTH_LONG).show();

    }
}