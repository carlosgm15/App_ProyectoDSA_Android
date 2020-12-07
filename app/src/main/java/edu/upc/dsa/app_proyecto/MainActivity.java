package edu.upc.dsa.app_proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Siguiente(View view) {
        Intent siguiente = new Intent(this, MainActivity2.class);
        startActivity(siguiente);
        //finish();
    }

    public void btnLogin_Click(View view) {
        final EditText usernameR = (EditText) findViewById(R.id.usernameLogin);
        final EditText passwordR = (EditText) findViewById(R.id.passwodLogin);
        String usuario = usernameR.getText().toString();
        String password = passwordR.getText().toString();

        if (usuario.isEmpty() && password.isEmpty())
            Toast.makeText(MainActivity.this,"Error: Inserte un usuario y una contraseña",Toast.LENGTH_LONG).show();
        else if (usuario.contentEquals("jose") && password.contentEquals("123"))
            Toast.makeText(MainActivity.this,"Loggin realizado: Usuario y contraseña correcta",Toast.LENGTH_LONG).show();
        else if (usuario.contentEquals("jose") && password.contentEquals("123")==false)
            Toast.makeText(MainActivity.this,"Error: Contraseña incorrecta",Toast.LENGTH_LONG).show();

    }
}