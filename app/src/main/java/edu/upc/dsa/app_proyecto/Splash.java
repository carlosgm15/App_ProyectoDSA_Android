package edu.upc.dsa.app_proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    String username = ReadSharedPreferenceUsername("nameUser");
    String pass = ReadSharedPreferencePassword("passwordUser");
    private String ReadSharedPreferenceUsername(String key){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key,"");
    }
    private String ReadSharedPreferencePassword(String key){
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key,"");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Handler h = new Handler();
        if(username==""||pass=="") {
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Open the Login Activity after 5 seconds
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
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);
                    finish();
                }
            }, 5000);

        }

    }
}