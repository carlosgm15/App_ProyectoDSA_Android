package edu.upc.dsa.app_proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
   /* public void showProgress (boolean visible){
        if(visible)
            this.splashbar.setVisibility(View.VISIBLE);
        else
            this.splashbar.setVisibility(View.GONE);
    }*/
    private String ReadSharedPreference(String key){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key,"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        splashbar =findViewById(R.id.indeterminateBar);
        splashbar.setVisibility(View.VISIBLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.username = ReadSharedPreference("nameUser");
        this.pass = ReadSharedPreference("passwordUser");

        final Handler h = new Handler();
        if(username==""||pass=="") {
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Open the Login Activity after 5 seconds
                    splashbar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 5000);
        }
        else {
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
    }
}