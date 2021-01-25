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
    public TextView dinero;
    public int Dinero, Prueba;
    Objetos bolsabasura, mascarilla, pocion, regeneron, pcr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tienda);
        boton = (Button) findViewById(R.id.buttonBotiquin);
        boton.setText("boton");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            id = bundle.getString("id");
        }
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
        User Usuario = new User();
        //
        Usuario.setUsername(name);
        Usuario.setId(id);
        try {
           GetUser(id);
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

        try {
            Call<Void> objetosCall2 = userService.addobjeto(regeneron);
            // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
            objetosCall2.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    Log.i("marc2", "" + response.code());
                    if (response.code() == 201) {
                        Log.i("marc2", "todo bien");
                        NotifyUser("Succesful");
                    }

                    if (response.code() == 400) {
                        NotifyUser("Bad request");
                    }

                    if (response.code() == 402) {
                        NotifyUser("No tienes dinero");
                    }

                    if (response.code() == 409) {
                        NotifyUser("Ya tienes comprado este objeto");
                    }

                }


                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("marc2", "Hay un error", t);
                    NotifyUser("Error server");

                }
            });
            usuario.setDinero(usuario.getDinero()-regeneron.getCoste());
            UpdateUser(usuario);
            GetUser(usuario.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onButtonPocionClick(View view) {
        pocion.setUserId(usuario.getId());
        try {

            Call<Void> objetosCall2 = userService.addobjeto(pocion);
            // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
            objetosCall2.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    Log.i("marc2", "" + response.code());
                    if (response.code() == 201) {
                        Log.i("marc2", "todo bien");
                        NotifyUser("Succesful");
                    }

                    if (response.code() == 400) {
                        NotifyUser("Bad request");
                    }

                    if (response.code() == 402) {
                        NotifyUser("No tienes dinero");
                    }

                    if (response.code() == 409) {
                        NotifyUser("Ya tienes comprado este objeto");
                    }

                }


                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("marc2", "Hay un error", t);
                    NotifyUser("Error server");

                }
            });

            usuario.setDinero(usuario.getDinero()-pocion.getCoste());
            UpdateUser(usuario);
            GetUser(usuario.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onButtonPcrClick(View view) {
        pcr.setUserId(usuario.getId());
        try {
            Call<Void> objetosCall2 = userService.addobjeto(pcr);
            // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
            objetosCall2.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    Log.i("marc2", "" + response.code());
                    if (response.code() == 201) {
                        Log.i("marc2", "todo bien");
                        NotifyUser("Succesful");
                    }

                    if (response.code() == 400) {
                        NotifyUser("Bad request");
                    }

                    if (response.code() == 402) {
                        NotifyUser("No tienes dinero");
                    }

                    if (response.code() == 409) {
                        NotifyUser("Ya tienes comprado este objeto");
                    }

                }


                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("marc2", "Hay un error", t);
                    NotifyUser("Error server");

                }
            });
            usuario.setDinero(usuario.getDinero()-pcr.getCoste());
            UpdateUser(usuario);
            GetUser(usuario.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onButtonBasuraClick(View view) {
        bolsabasura.setUserId(usuario.getId());
        try {
            Call<Void> objetosCall2 = userService.addobjeto(bolsabasura);
            // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
            objetosCall2.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    Log.i("marc2", "" + response.code());
                    if (response.code() == 201) {
                        Log.i("marc2", "todo bien");
                        NotifyUser("Succesful");
                    }

                    if (response.code() == 400) {
                        NotifyUser("Bad request");
                    }

                    if (response.code() == 402) {
                        NotifyUser("No tienes dinero");
                    }

                    if (response.code() == 409) {
                        NotifyUser("Ya tienes comprado este objeto");
                    }

                }


                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("marc2", "Hay un error", t);
                    NotifyUser("Error server");

                }
            });
            usuario.setDinero(usuario.getDinero()-bolsabasura.getCoste());
            UpdateUser(usuario);
            GetUser(usuario.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onButtonMascarillaClick(View view) {
        mascarilla.setUserId(usuario.getId());
        try {

            Call<Void> objetosCall2 = userService.addobjeto(mascarilla);
            // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
            objetosCall2.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                    Log.i("marc2", "" + response.code());
                    if (response.code() == 201) {
                        Log.i("marc2", "todo bien");
                        NotifyUser("Succesful");
                    }

                    if (response.code() == 400) {
                        NotifyUser("Bad request");
                    }

                    if (response.code() == 402) {
                        NotifyUser("No tienes dinero");
                    }

                    if (response.code() == 409) {
                        NotifyUser("Ya tienes comprado este objeto");
                    }

                }


                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("marc2", "Hay un error", t);
                    NotifyUser("Error server");

                }
            });

            usuario.setDinero(usuario.getDinero()-mascarilla.getCoste());
            UpdateUser(usuario);
            GetUser(usuario.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void NotifyUser(String MSG) {

        Toast toast = Toast.makeText(Activity_Tienda.this, MSG, Toast.LENGTH_SHORT);
    }
    public void GetUser(String id){
        Call<User> usersCall = userService.gettuser(id);
        // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
        usersCall.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("marc", "" + response.code());
                usuario = response.body();
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

    }
    public void UpdateUser(User User){
        Call<User> UserCall3 = userService.updateuser(User);
        // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
        UserCall3.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("marc2", "" + response.code());
                usuario = response.body();
                if (response.code() == 201) {
                    Log.i("marc2", "todo bien3");
                    NotifyUser("Succesful");
                    dinero.setText("Dinero = " + usuario.getDinero());

                }

                if (response.code() == 400) {
                    NotifyUser("Bad request");
                    dinero.setText("Dinero = " + usuario.getDinero());
                }
                if (response.code() == 404) {
                    NotifyUser("Not found");
                    dinero.setText("Dinero = " + usuario.getDinero());
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}
