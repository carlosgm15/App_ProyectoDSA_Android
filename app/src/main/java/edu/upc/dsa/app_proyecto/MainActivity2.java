package edu.upc.dsa.app_proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //no se si esto se debe hacer aqui o en las funciones de los botontes
        final EditText nombreT = (EditText)findViewById(R.id.nombreRegistro);
        final EditText apellidosT = (EditText)findViewById(R.id.apellidoRegistro);
        final EditText usernameT = (EditText)findViewById(R.id.UsernameRegistro);
        final EditText passwordT = (EditText)findViewById(R.id.passwodRegistro);
        final EditText pasword2T = (EditText)findViewById(R.id.pasword2Registro);

    }

    public void Anterior(View view){
        Intent anterior = new Intent(this, MainActivity.class);
        startActivity(anterior);
    }

    public void btnRegistro_Click(View view){
        final EditText nombreT = (EditText)findViewById(R.id.nombreRegistro);
        final EditText apellidosT = (EditText)findViewById(R.id.apellidoRegistro);
        final EditText usernameT = (EditText)findViewById(R.id.UsernameRegistro);
        final EditText passwordT = (EditText)findViewById(R.id.passwodRegistro);
        final EditText pasword2T = (EditText)findViewById(R.id.pasword2Registro);
        String nombre = nombreT.getText().toString();
        String apellidos = apellidosT.getText().toString();
        String usuario = usernameT.getText().toString();
        String password = passwordT.getText().toString();
        String password2 = pasword2T.getText().toString();

        if (password.isEmpty()&& nombre.isEmpty() && usuario.isEmpty())
            Toast.makeText(MainActivity2.this,"No has introducido valores",Toast.LENGTH_LONG).show();
        Intent Intent = new Intent();
        Intent.putExtra("username2", usuario);
        Intent.putExtra("password2",password);
        setResult(RESULT_OK,Intent);
        finish();
    }
}