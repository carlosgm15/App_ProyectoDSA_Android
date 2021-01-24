package activities_dash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import edu.upc.dsa.app_proyecto.R;

public class Activity_Ajustes extends AppCompatActivity {
RelativeLayout rellay_eliminar,rellay_id;
private String name, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        Bundle bundle=getIntent().getExtras();
        rellay_id = findViewById(R.id.rellay_id);
        rellay_eliminar = findViewById(R.id.rellay_eliminar);
        if(bundle!=null)
            name=bundle.getString("name");
            id=bundle.getString("id");

        rellay_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Ajustes.this, Activity_ChangePass.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        rellay_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Ajustes.this, Activity_delete.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}