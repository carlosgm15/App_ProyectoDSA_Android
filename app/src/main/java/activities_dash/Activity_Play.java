package activities_dash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Activity_Play extends AppCompatActivity {
    private String name, id;
    private TextView texto;
    Retrofit retrofit;
    UserService userService;
    List<Objetos> lista;
    User usuario;
    private int bolsabasura, mascarilla, pocion, regeneron,pcr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__play);
        texto = findViewById(R.id.prueba);
        Bundle bundle=getIntent().getExtras();
        bolsabasura = 0;
        mascarilla = 0;
        pocion = 0;
        regeneron = 0;
        pcr = 0;
        if(bundle!=null) {
            name = bundle.getString("name");
            id=bundle.getString("id");
        }

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

                        Activity_Play.this.usuario = response.body();
                        lista = usuario.objetosList;


                        for (Objetos i: lista){
                            String objeto = i.getNombre();
                            if(objeto.equals("bolsabasura"))
                                bolsabasura++;
                            if(objeto.equals("mascarilla"))
                                mascarilla++;
                            if(objeto.equals("pocion"))
                                pocion++;
                            if(objeto.equals("regeneron"))
                                regeneron++;
                            if(objeto.equals("pcr"))
                                pcr++;
                        }

                        String msg ="";
                        msg= msg+ usuario.getUsername();
                        msg = msg +" "+bolsabasura+" "+mascarilla+ " "+pocion+
                                " "+regeneron+" "+pcr;
                        texto.setText(msg);

                        /*Intent intent = new Intent(this, UnityPlayerActivity.class);
                        intent.putExtra("arguments", );
                        startActivity(intent);*/


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

        Toast toast = Toast.makeText(Activity_Play.this, MSG, Toast.LENGTH_SHORT);
    }
    }
