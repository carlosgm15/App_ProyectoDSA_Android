package activities_dash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import edu.upc.dsa.app_proyecto.R;

public class Activity_Score extends AppCompatActivity {
RelativeLayout rellay_ind,rellay_coop;
    private String name, id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        rellay_ind = findViewById(R.id.rellay_ind);
        rellay_coop = findViewById(R.id.rellay_coop);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            name = bundle.getString("name");
            id=bundle.getString("id");
        }


        rellay_ind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Score.this, Activity_ScoreInd.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        rellay_coop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Score.this, Activity_ScoreCoop.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}