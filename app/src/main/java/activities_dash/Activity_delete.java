package activities_dash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class Activity_delete extends AppCompatActivity {
    String name, id, pass;
    Retrofit retrofit;
    public User usuario;
    UserService userService;
    public TextView editTextPass;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__delete);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            id = bundle.getString("id");
        }
        delete = this.findViewById(R.id.buttonChange);
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
        Call<User> usersCall = userService.gettuser(id);
        // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
        usersCall.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i("marc", "" + response.code());
                if (response.code() == 201) {
                    NotifyUser("Successful");
                    usuario = response.body();
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

    public void DeleteClick(View view) {
        try {

            Call<Void> usersCall2 = userService.deleteuser(usuario);
            // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
            usersCall2.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.i("marc", "" + response.code());
                    if (response.code() == 201) {
                        Log.i("marc", "todo bien");
                        NotifyUser("Succesful");
                        Intent intent = new Intent(Activity_delete.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }

                    if (response.code() == 400) {
                        NotifyUser("Bad request");
                        Intent intent = new Intent(Activity_delete.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }

                    if (response.code() == 404) {
                        NotifyUser("No encontrado");
                        Intent intent = new Intent(Activity_delete.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void NotifyUser(String MSG) {

        Toast toast = Toast.makeText(Activity_delete.this, MSG, Toast.LENGTH_SHORT);
    }
}