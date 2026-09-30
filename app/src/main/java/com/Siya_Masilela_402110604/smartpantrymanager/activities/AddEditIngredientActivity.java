package com.Siya_Masilela_402110604.smartpantrymanager.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.Siya_Masilela_402110604.smartpantrymanager.R;
import com.Siya_Masilela_402110604.smartpantrymanager.database.DatabaseHelper;
import com.Siya_Masilela_402110604.smartpantrymanager.models.PantryItem;
import com.google.android.material.textfield.TextInputEditText;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AddEditIngredientActivity extends AppCompatActivity {

    private TextInputEditText etName, etQuantity, etUnit, etExpiry;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    private String mode;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);
        databaseHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbarAddEdit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etName = findViewById(R.id.etIngredientName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiry = findViewById(R.id.etExpiryDate);
        btnSave = findViewById(R.id.btnSaveIngredient);

        mode = getIntent().getStringExtra("mode");
        if ("edit".equals(mode)) {
            getSupportActionBar().setTitle(R.string.title_edit_ingredient);
            btnSave.setText(R.string.btn_update_ingredient);
            itemId = getIntent().getIntExtra("item_id", -1);
            etName.setText(getIntent().getStringExtra("item_name"));
            etQuantity.setText(String.valueOf(getIntent().getDoubleExtra("item_quantity", 0)));
            etUnit.setText(getIntent().getStringExtra("item_unit"));
            etExpiry.setText(getIntent().getStringExtra("item_expiry"));
        } else {
            getSupportActionBar().setTitle(R.string.title_add_ingredient);
            btnSave.setText(R.string.btn_save_ingredient);
        }

        btnSave.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {
        String name = etName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        if (TextUtils.isEmpty(name)) { etName.setError(getString(R.string.error_required)); etName.requestFocus(); return; }
        if (TextUtils.isEmpty(quantityStr)) { etQuantity.setError(getString(R.string.error_required)); etQuantity.requestFocus(); return; }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityStr);
            if (quantity <= 0) { etQuantity.setError(getString(R.string.error_quantity_positive)); etQuantity.requestFocus(); return; }
        } catch (NumberFormatException e) {
            etQuantity.setError(getString(R.string.error_invalid_number)); etQuantity.requestFocus(); return;
        }
        if (TextUtils.isEmpty(unit)) { etUnit.setError(getString(R.string.error_required)); etUnit.requestFocus(); return; }
        if (!TextUtils.isEmpty(expiry) && !isValidDate(expiry)) {
            etExpiry.setError(getString(R.string.error_invalid_date)); etExpiry.requestFocus(); return;
        }

        PantryItem item = new PantryItem(itemId, name, quantity, unit, expiry);

        if ("edit".equals(mode)) {
            databaseHelper.updatePantryItem(item);
            Toast.makeText(this, R.string.toast_ingredient_updated, Toast.LENGTH_SHORT).show();
        } else {
            long result = databaseHelper.addPantryItem(item);
            if (result != -1) Toast.makeText(this, R.string.toast_ingredient_added, Toast.LENGTH_SHORT).show();
            else { Toast.makeText(this, R.string.toast_error, Toast.LENGTH_SHORT).show(); return; }
        }
        finish();
    }

    private boolean isValidDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        format.setLenient(false);
        try {
            format.parse(date);
            return date.matches("\\d{4}-\\d{2}-\\d{2}");
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { finish(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
