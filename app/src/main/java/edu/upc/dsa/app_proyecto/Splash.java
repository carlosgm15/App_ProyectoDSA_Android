package edu.upc.dsa.app_proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class Splash extends AppCompatActivity {

    String username = null;
    String pass = null;
    ProgressBar splashbar;

   /* private void ReadSharedPreference() {
        SharedPreferences sharedPref = getSharedPreferences("credenciales", MODE_PRIVATE);
        String username = sharedPref.getString("nameUser", "");
        String pass = sharedPref.getString("pass", "");
    }
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashbar =findViewById(R.id.progressBar);
        splashbar.setVisibility(View.VISIBLE);
       // ReadSharedPreference();

        final Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Open the Login Activity after 5 seconds
                    //splashbar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 5000);
        }
        /*else {
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Open the Login Activity after 5 seconds
                    splashbar.setVisibility(View.INVISIBLE);
                    Intent intent2 = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent2);
                    finish();
                }
            }, 5000);

        }
    }*/
}