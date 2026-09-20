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
            getSupportActionBar().setTitle("Edit Ingredient");
            btnSave.setText("Update Ingredient");
            itemId = getIntent().getIntExtra("item_id", -1);
            etName.setText(getIntent().getStringExtra("item_name"));
            etQuantity.setText(String.valueOf(getIntent().getDoubleExtra("item_quantity", 0)));
            etUnit.setText(getIntent().getStringExtra("item_unit"));
            etExpiry.setText(getIntent().getStringExtra("item_expiry"));
        } else {
            getSupportActionBar().setTitle("Add Ingredient");
            btnSave.setText("Save Ingredient");
        }

        btnSave.setOnClickListener(v -> saveIngredient());
    }

    private void saveIngredient() {
        String name = etName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        if (TextUtils.isEmpty(name)) { etName.setError("Required"); etName.requestFocus(); return; }
        if (TextUtils.isEmpty(quantityStr)) { etQuantity.setError("Required"); etQuantity.requestFocus(); return; }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityStr);
            if (quantity <= 0) { etQuantity.setError("Must be > 0"); etQuantity.requestFocus(); return; }
        } catch (NumberFormatException e) {
            etQuantity.setError("Invalid number"); etQuantity.requestFocus(); return;
        }
        if (TextUtils.isEmpty(unit)) { etUnit.setError("Required"); etUnit.requestFocus(); return; }

        PantryItem item = new PantryItem(itemId, name, quantity, unit, expiry);

        if ("edit".equals(mode)) {
            databaseHelper.updatePantryItem(item);
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        } else {
            long result = databaseHelper.addPantryItem(item);
            if (result != -1) Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            else { Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show(); return; }
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { finish(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
