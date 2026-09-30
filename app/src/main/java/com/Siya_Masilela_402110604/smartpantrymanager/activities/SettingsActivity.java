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

    public static final String PREFS_NAME = "SmartPantryPrefs";
    public static final String KEY_EXPIRY_ALERTS = "expiry_alerts";
    public static final String KEY_UNIT_PREFERENCE = "unit_preference";
    public static final String UNIT_METRIC = "metric";
    public static final String UNIT_IMPERIAL = "imperial";

    private SwitchCompat switchExpiryAlerts;
    private RadioGroup radioGroupUnits;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbarSettings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);
        radioGroupUnits = findViewById(R.id.radioGroupUnits);
        loadSettings();

        switchExpiryAlerts.setOnCheckedChangeListener((b, c) -> {
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putBoolean(KEY_EXPIRY_ALERTS, c);
            e.apply();
        });

        radioGroupUnits.setOnCheckedChangeListener((g, id) -> {
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putString(KEY_UNIT_PREFERENCE, id == R.id.radioMetric ? UNIT_METRIC : UNIT_IMPERIAL);
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
        switchExpiryAlerts.setChecked(sharedPreferences.getBoolean(KEY_EXPIRY_ALERTS, true));
        String unitPref = sharedPreferences.getString(KEY_UNIT_PREFERENCE, UNIT_METRIC);
        if (UNIT_IMPERIAL.equals(unitPref)) radioGroupUnits.check(R.id.radioImperial);
        else radioGroupUnits.check(R.id.radioMetric);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { finish(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
