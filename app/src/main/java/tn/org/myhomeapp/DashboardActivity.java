package tn.org.myhomeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DashboardActivity extends AppCompatActivity {

    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_actvity);
        setTitle(getString(R.string.values));
        data = getIntent().getStringExtra("id");
        Bundle b = new Bundle();
        b.putString("node", data);
        TempFragment frag = new TempFragment();
        frag.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host
                        , frag, "TEMP").commit();
        BottomNavigationView navigation = findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Bundle b = new Bundle();
        b.putString("node", data);
        switch (item.getItemId()) {
            case R.id.temp:
                TempFragment frag = new TempFragment();
                frag.setArguments(b);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host
                                , frag, "TEMP").commit();
                return true;
            case R.id.humidity:
                HumidityFragment frag2 = new HumidityFragment();
                frag2.setArguments(b);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host
                                , frag2, "HUM").commit();
                return true;
        }
        return false;
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
            View dialogView = LayoutInflater.from(this)
                    .inflate(R.layout.setting_dialog_view, null, false);
            new MaterialAlertDialogBuilder(this)
                    .setView(dialogView)
                    .setTitle("Add")
                    .setMessage("Add terminal")
                    .setPositiveButton("Add", (dialog, which) -> {
                        int maxHum = Integer.parseInt(((EditText) dialogView.findViewById(R.id.max_hum)).getText().toString());
                        int maxTmp = Integer.parseInt(((EditText) dialogView.findViewById(R.id.max_temp)).getText().toString());

                        setData(maxHum, maxTmp);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    }).show();
        }
        return true;
    }

    private void setData(int maxHum, int maxTmp) {
        Map<String, Object> map = new HashMap<>();
        map.put("hum", maxHum);
        map.put("temp", maxTmp);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).child(data).updateChildren(map);
    }
}