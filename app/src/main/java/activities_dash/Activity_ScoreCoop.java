package activities_dash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Models.Objetos;
import Models.ObjetosList;
import Models.Ranking;
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

public class Activity_ScoreCoop extends AppCompatActivity {
    Retrofit retrofit;
    UserService userService;
    List<User> lista;
    User user1,user2,user3;
    String name,id;
    TextView user1view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__score_coop);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            id = bundle.getString("id");
        }
        user1view = this.findViewById(R.id.user1);
        Log.i("marc2", "llego aqui");
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //Attaching Interceptor to a client
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://147.83.7.204:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        User usuariotmp2 = new User();
        //
        usuariotmp2.setUsername(name);
        usuariotmp2.setId(id);
        try {
            Call<List<User>> UserCall = userService.getranking();
            // Android Doesn't allow synchronous execution of Http Request and so we must put it in queue
            UserCall.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    Log.i("marc", ""+response.code());
                    Ranking List = Ranking.getInstance();
                    List.setList(response.body());
                    user1 = List.getList().get(0);
                    user2 = List.getList().get(1);
                    user3 = List.getList().get(2);
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    NotifyUser("Error Server");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void NotifyUser(String MSG) {

        Toast toast = Toast.makeText(Activity_ScoreCoop.this, MSG, Toast.LENGTH_SHORT);
    }
}
