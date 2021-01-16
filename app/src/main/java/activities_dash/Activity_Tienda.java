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
    List<Objetos> objetosList;
    User usuario;
    public TextView dinero ;
    public int Dinero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tienda);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
            name=bundle.getString("name");
            id=bundle.getString("id");
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
        User usuariotmp = new User();
        //
        usuariotmp.setUsername(name);
        usuariotmp.setId(id);
        try {
            Call<User> usersCall = userService.gettuser(usuariotmp);
            // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
            usersCall.enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.i("marc", ""+response.code());
                    if (response.code() == 201) {
                        NotifyUser("Successful");
                        Activity_Tienda.this.usuario = response.body();
                        objetosList = usuario.objetosList;
                        Dinero = usuario.getDinero();
                        dinero.setText("Dinero = "+ dinero);

                    }

                    if (response.code() == 404) {
                        NotifyUser("Error ocurrido");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("marc", "on failure",t);
                    NotifyUser("Error Server");
                }

            });
        } catch (Exception e) {

            NotifyUser("Exception: " + e.toString());
        }
    }
    private void NotifyUser(String MSG){

        Toast toast = Toast.makeText(Activity_Tienda.this,MSG,Toast.LENGTH_SHORT);
    }
}
