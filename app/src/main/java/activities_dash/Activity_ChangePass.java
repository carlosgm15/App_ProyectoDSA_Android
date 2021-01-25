package activities_dash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class Activity_ChangePass extends AppCompatActivity {
    String name,id, pass;
    Retrofit retrofit;
    User usuario;
    UserService userService;
    public TextView editTextPass;
    Button change;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__changepass);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            id = bundle.getString("id");
        }
        change = this.findViewById(R.id.buttonChange);
        editTextPass = this.findViewById(R.id.password);
        }

    private void NotifyUser(String MSG) {

        Toast toast = Toast.makeText(Activity_ChangePass.this, MSG, Toast.LENGTH_SHORT);
    }
    public void ChangeClick (View view) {
        pass = editTextPass.getText().toString();
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
        usuariotmp2.setPassword(pass);
        try {
            Call<User> usersCall = userService.updatepassword(usuariotmp2);
            // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
            usersCall.enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Log.i("marc", "" + response.code());
                    if (response.code() == 201) {
                        NotifyUser("Successful");
                        Activity_ChangePass.this.usuario = response.body();

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

}
