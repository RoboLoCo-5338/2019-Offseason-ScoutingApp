package org.team5338.scouting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addMatch = findViewById(R.id.add_match);
        addMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MatchActivity.class);
                intent.putExtra("scouter", ((EditText) findViewById(R.id.scouter)).getText());
                startActivity(intent);
            }
        });

        refreshList(null);

        setActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    protected void refreshList(View view) {

        final float scale = getResources().getDisplayMetrics().density;
        int padding_5dp = (int) (5 * scale + 0.5f);
        int padding_20dp = (int) (20 * scale + 0.5f);
        int padding_50dp = (int) (50 * scale + 0.5f);

        ViewGroup vertLayout = findViewById(R.id.vert_layout_main);
        vertLayout.removeAllViews();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/frc_scouting";
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files == null)
            return;
        for (int i = 0; i < files.length; i++) {
            LinearLayout horizLayout = new LinearLayout(getApplicationContext());
            horizLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView vw = new TextView(getApplicationContext());
            vw.setText(files[i].getName());
            horizLayout.addView(vw);
            horizLayout.setPadding(padding_20dp, padding_20dp, padding_20dp, padding_20dp);
            vertLayout.addView(horizLayout);
        }

    }
}
