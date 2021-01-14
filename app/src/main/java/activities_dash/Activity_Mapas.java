package activities_dash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.upc.dsa.app_proyecto.R;

public class Activity_Mapas extends AppCompatActivity {
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapas);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
            name=bundle.getString("nombre");
    }
}