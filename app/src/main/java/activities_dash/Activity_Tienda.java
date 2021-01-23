package activities_dash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Models.Objetos;
import Models.ObjetosList;
import Models.User;
import edu.upc.dsa.app_proyecto.Dashboard;
import edu.upc.dsa.app_proyecto.MainActivity;
import edu.upc.dsa.app_proyecto.R;
import edu.upc.dsa.app_proyecto.UserService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Tienda extends AppCompatActivity {
    private String name, id;
    int coste, coste2;
    Retrofit retrofit;
    Button boton;
    //para la interfaz
    UserService userService;
    List<Objetos> lista;
    User usuario;
    public TextView dinero, prueba;
    public int Dinero, Prueba;
    Objetos bolsabasura, mascarilla, pocion, regeneron, pcr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tienda);
        boton = (Button) findViewById(R.id.buttonBotiquin);
        boton.setText("boton");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            name = bundle.getString("name");
        id = bundle.getString("id");
        dinero = this.findViewById(R.id.dinero);
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
        User usuariotmp2 = new User();
        //
        usuariotmp2.setUsername(name);
        usuariotmp2.setId(id);
        try {
            Call<User> usersCall = userService.gettuser(id);
            // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
            usersCall.enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.i("marc", "" + response.code());
                    if (response.code() == 201) {
                        NotifyUser("Successful");
                        Activity_Tienda.this.usuario = response.body();
                        lista = usuario.objetosList;
                        Dinero = usuario.getDinero();
                        dinero.setText("Dinero = " + Dinero);

                    }

                    if (response.code() == 404) {
                        NotifyUser("Error ocurrido");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("marc", "on failure", t);
                    NotifyUser("Error Server");
                }

            });
            Call<List<Objetos>> objetosCall = userService.getobjetos();
            // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
            objetosCall.enqueue(new Callback <List<Objetos>>() {
                @Override
                public void onResponse(Call<List<Objetos>> call, Response<List<Objetos>> response) {
                        ObjetosList List = ObjetosList.getInstance();
                        List.setList(response.body());
                        bolsabasura = List.getList().get(0);
                        mascarilla = List.getList().get(1);
                        pocion = List.getList().get(2);
                        regeneron = List.getList().get(3);
                        pcr = List.getList().get(4);
                }

                @Override
                public void onFailure(Call<List<Objetos>> call, Throwable t) {
                    NotifyUser("Error Server");
                }
            });
        }

        catch (Exception e) {

            NotifyUser("Exception: " + e.toString());
        }
    }
    public void onButtonBotiquinClick(View view) {
        regeneron.setUserId(usuario.getId());
        Call<Objetos> objetosCall2 = userService.addobjeto(regeneron);
        // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
        objetosCall2.enqueue(new Callback <Objetos>() {
            @Override
            public void onResponse(Call<Objetos> call, Response<Objetos> response) {
                if(response.code() == 201){
                    NotifyUser("Succesful");
                    dinero.setText("Dinero = " + usuario.getDinero());

                }

                if(response.code() == 400){
                    NotifyUser("Bad request");
                    dinero.setText("Dinero = " + usuario.getDinero());
                }

                if(response.code() == 402){
                    NotifyUser("No tienes dinero");
                    dinero.setText("Dinero = " + usuario.getDinero());
                }

                if(response.code() == 409){
                    NotifyUser("Ya tienes comprado este objeto");
                    dinero.setText("Dinero = " + usuario.getDinero());
                }

            }

            @Override
            public void onFailure(Call<Objetos> call, Throwable t) {
                NotifyUser("Error server");
            }
        });
    }
    public void onButtonPocionClick(View view) {
        usuario.objetosList.add(pocion);
        coste = usuario.getDinero();
        coste2 = pocion.getCoste();
        coste = coste-coste2;
        usuario.setDinero(coste);
        dinero.setText("Dinero = " + usuario.getDinero());

    }
    public void onButtonPcrClick(View view) {
        usuario.objetosList.add(pcr);
        coste = usuario.getDinero();
        coste2 = pcr.getCoste();
        coste = coste-coste2;
        usuario.setDinero(coste);
        dinero.setText("Dinero = " + usuario.getDinero());

    }
    public void onButtonBasuraClick(View view) {
        usuario.objetosList.add(bolsabasura);
        coste = usuario.getDinero();
        coste2 = bolsabasura.getCoste();
        coste = coste-coste2;
        usuario.setDinero(coste);
        dinero.setText("Dinero = " + usuario.getDinero());

    }
    public void onButtonMascarillaClick(View view) {
        usuario.objetosList.add(mascarilla);
        coste = usuario.getDinero();
        coste2 = mascarilla.getCoste();
        coste = coste-coste2;
        usuario.setDinero(coste);
        dinero.setText("Dinero = " + usuario.getDinero());

    }

    private void NotifyUser(String MSG) {

        Toast toast = Toast.makeText(Activity_Tienda.this, MSG, Toast.LENGTH_SHORT);
    }
    public void GetObjetos(){

    }
}
