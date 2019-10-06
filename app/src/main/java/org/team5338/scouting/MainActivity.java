package org.team5338.scouting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        setContentView(R.layout.activity_match);

        setPlusMinus(R.id.sandstorm_hatch_minus, R.id.sandstorm_hatch_plus, R.id.sandstorm_hatch);
        setPlusMinus(R.id.sandstorm_cargo_minus, R.id.sandstorm_cargo_plus, R.id.sandstorm_cargo);
        setPlusMinus(R.id.teleop_hatch_minus, R.id.teleop_hatch_plus, R.id.teleop_hatch);
        setPlusMinus(R.id.teleop_cargo_minus, R.id.teleop_cargo_plus, R.id.teleop_cargo);

        FloatingActionButton submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray array = new JSONArray();
                JSONObject json = new JSONObject();
                try {
                    json.put("match_number", Integer.parseInt(((EditText) findViewById(R.id.match_number)).getText().toString()));
                    json.put("team_number", Integer.parseInt(((EditText) findViewById(R.id.team_number)).getText().toString()));
                    json.put("starting_pos", ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.starting_group)).getCheckedRadioButtonId())).getText().toString());
                    json.put("sandstorm_hatch", Integer.parseInt(((TextView) findViewById(R.id.sandstorm_hatch)).getText().toString()));
                    json.put("sandstorm_cargo", Integer.parseInt(((TextView) findViewById(R.id.sandstorm_cargo)).getText().toString()));
                    json.put("teleop_hatch", Integer.parseInt(((TextView) findViewById(R.id.teleop_hatch)).getText().toString()));
                    json.put("teleop_cargo", Integer.parseInt(((TextView) findViewById(R.id.teleop_cargo)).getText().toString()));
                    json.put("ending_pos", ((RadioButton) findViewById(((RadioGroup) findViewById(R.id.ending_group)).getCheckedRadioButtonId())).getText().toString());
                    array.put(json);

                    File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/frc_scouting");
                    path.mkdirs();
                    File file = new File(path.getAbsolutePath() + "/match" + json.getInt("match_number")+"_team" + json.getInt("team_number") + ".json");
                    Log.i("filepath", file.getAbsolutePath());
                    Log.i("json", array.toString());
                    if (!file.exists()) {
                        file.createNewFile();
                        FileWriter writer = new FileWriter(file);
                        writer.write(array.toString() + "\n");
                        writer.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Wrong input!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void setPlusMinus(int minus, int plus, final int field) {
        Button minusButton = findViewById(minus);
        Button plusButton = findViewById(plus);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView fieldText = findViewById(field);
                fieldText.setText((Integer.parseInt(fieldText.getText().toString()) - 1) + "");
            }
        });
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView fieldText = findViewById(field);
                fieldText.setText((Integer.parseInt(fieldText.getText().toString()) + 1) + "");
            }
        });
    }
}
