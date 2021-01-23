package activities_dash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Models.Objetos;
import Models.User;
import edu.upc.dsa.app_proyecto.R;
import edu.upc.dsa.app_proyecto.UserService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_ScoreInd extends AppCompatActivity {
    String name, id;
    Retrofit retrofit;
    UserService userService;
    List<Objetos> lista;
    User usuario;
    public TextView Nombre, IdUser, PuntosDefensa, PuntosSalud, Dinero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__score_ind);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            name = bundle.getString("name");
            id=bundle.getString("id");
        }
        Dinero = this.findViewById(R.id.Dinero);
        IdUser = this.findViewById(R.id.Id);
        PuntosDefensa = this.findViewById(R.id.PuntosDefensa);
        PuntosSalud = this.findViewById(R.id.PuntosSalud);
        Nombre = this.findViewById(R.id.nombreUser);
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
                        Activity_ScoreInd.this.usuario = response.body();
                        lista = usuario.objetosList;
                        Dinero.setText("Dinero = " + usuario.getDinero());
                        IdUser.setText("Id usuario = " + usuario.getId());
                        PuntosDefensa.setText("Puntos defensa = " + usuario.getDefensa());
                        PuntosSalud.setText("Puntos salud = " + usuario.getVida());
                        Nombre.setText("Nombre= " + usuario.getUsername());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void NotifyUser(String MSG) {

        Toast toast = Toast.makeText(Activity_ScoreInd.this, MSG, Toast.LENGTH_SHORT);
    }
}