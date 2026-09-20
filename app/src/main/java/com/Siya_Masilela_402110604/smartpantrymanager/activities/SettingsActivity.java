package com.Siya_Masilela_402110604.smartpantrymanager.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import com.Siya_Masilela_402110604.smartpantrymanager.R;
import com.Siya_Masilela_402110604.smartpantrymanager.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchExpiryAlerts;
    private RadioGroup radioGroupUnits;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferences = getSharedPreferences("SmartPantryPrefs", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);
        radioGroupUnits = findViewById(R.id.radioGroupUnits);
        loadSettings();

        switchExpiryAlerts.setOnCheckedChangeListener((b, c) -> {
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putBoolean("expiry_alerts", c);
            e.apply();
        });

        radioGroupUnits.setOnCheckedChangeListener((g, id) -> {
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putString("unit_preference", id == R.id.radioMetric ? "metric" : "imperial");
            e.apply();
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationSettings);
        bottomNavigationView.setSelectedItemId(R.id.nav_settings);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_pantry) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_recipes) {
                startActivity(new Intent(SettingsActivity.this, SuggestedRecipesActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_settings) {
                return true;
            }
            return false;
        });
    }

    private void loadSettings() {
        switchExpiryAlerts.setChecked(sharedPreferences.getBoolean("expiry_alerts", true));
        String unitPref = sharedPreferences.getString("unit_preference", "metric");
        if (unitPref.equals("imperial")) radioGroupUnits.check(R.id.radioImperial);
        else radioGroupUnits.check(R.id.radioMetric);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { finish(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
