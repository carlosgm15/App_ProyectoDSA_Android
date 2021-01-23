package activities_dash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    Retrofit retrofit;
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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            name = bundle.getString("name");
        id = bundle.getString("id");
        dinero = this.findViewById(R.id.dinero);
        prueba = this.findViewById(R.id.prueba);
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
                       // Prueba = List[0];
                        //prueba.setText("Prueba = " + List.size());
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

    private void NotifyUser(String MSG) {

        Toast toast = Toast.makeText(Activity_Tienda.this, MSG, Toast.LENGTH_SHORT);
    }
    public void GetObjetos(){

    }
}
