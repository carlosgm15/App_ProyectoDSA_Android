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
    User user1,user2,user3,user4,user5,user6,user7,user8,user9,user10;
    String name,id;
    TextView userV1, userV2,userV3,userV4,userV5,userV6,userV7,userV8,userV9,userV10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__score_coop);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            name = bundle.getString("name");
            id = bundle.getString("id");
        }
        userV1 = this.findViewById(R.id.user1);
        userV2 = this.findViewById(R.id.user2);
        userV3 = this.findViewById(R.id.user3);
        userV4 = this.findViewById(R.id.user4);
        userV5 = this.findViewById(R.id.user5);
        userV6 = this.findViewById(R.id.user6);
        userV7 = this.findViewById(R.id.user7);
        userV8 = this.findViewById(R.id.user8);
        userV9 = this.findViewById(R.id.user9);
        userV10 = this.findViewById(R.id.user10);

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
        userService = retrofit.create(UserService.class);
        User usuario = new User();
        //
        usuario.setUsername(name);
        usuario.setId(id);
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
                    user4 = List.getList().get(3);
                    user5 = List.getList().get(4);
                    user6 = List.getList().get(5);
                    user7 = List.getList().get(6);
                    user8 = List.getList().get(7);
                    user9 = List.getList().get(8);
                    user10 = List.getList().get(9);
                    userV1.setText("El Numero 1 : "+user1.getUsername());
                    userV2.setText("El segundon : "+user2.getUsername());
                    userV3.setText("Con suerte pilla podium : "+user3.getUsername());
                    userV4.setText("El cuarto : "+user4.getUsername());
                    userV5.setText("El quinto : "+user5.getUsername());
                    userV6.setText("El sexto : "+user6.getUsername());
                    userV7.setText("EL septimo : "+user7.getUsername());
                    userV8.setText("El octavo : "+user8.getUsername());
                    userV9.setText("El noveno : "+user9.getUsername());
                    userV10.setText("El primero por la cola crack : "+user10.getUsername());

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
