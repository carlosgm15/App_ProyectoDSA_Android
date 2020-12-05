package edu.upc.dsa.app_proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final EditText nombreT = (EditText)findViewById(R.id.nombreRegistro);
        final EditText apellidosT = (EditText)findViewById(R.id.apellidoRegistro);
        final EditText usernameT = (EditText)findViewById(R.id.usernameLogin);
        final EditText passwordT = (EditText)findViewById(R.id.passwodRegistro);
        final EditText pasword2T = (EditText)findViewById(R.id.pasword2Registro);
        Button btnRegistro = (Button)findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String nombre = nombreT.getText().toString();
                String  apellidos= apellidosT.getText().toString();
                String username = usernameT.getText().toString();
                String password = passwordT.getText().toString();
                String password2 = pasword2T.getText().toString();


            }

        });

    }

    public void Anterior(View view){
        Intent anterior = new Intent(this, MainActivity.class);
        startActivity(anterior);
    }
}