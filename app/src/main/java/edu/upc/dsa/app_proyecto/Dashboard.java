package edu.upc.dsa.app_proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import activities_dash.Activity_Ajustes;
import activities_dash.Activity_Play;
import activities_dash.Activity_Score;
import activities_dash.Activity_Tienda;

public class Dashboard extends AppCompatActivity {

    RelativeLayout rellay_ajustes, rellay_tienda, rellay_score,
            rellay_play;
    private String name, id;
    public TextView Name;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        Log.i("marc", "Dashboard");

        rellay_ajustes = findViewById(R.id.rellay_ajustes);
        rellay_tienda = findViewById(R.id.rellay_tienda);
        rellay_score = findViewById(R.id.rellay_score);
        rellay_play = findViewById(R.id.rellay_play);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
            name=bundle.getString("name");
            id=bundle.getString("id");

        Name = this.findViewById(R.id.name);
        Name.setText(name);

        rellay_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Activity_Play.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        rellay_ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Activity_Ajustes.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        rellay_tienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Activity_Tienda.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        rellay_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Activity_Score.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

}
