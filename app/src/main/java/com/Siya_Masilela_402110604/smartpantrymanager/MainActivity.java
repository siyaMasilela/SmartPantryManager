package com.Siya_Masilela_402110604.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.Siya_Masilela_402110604.smartpantrymanager.activities.AddEditIngredientActivity;
import com.Siya_Masilela_402110604.smartpantrymanager.activities.SettingsActivity;
import com.Siya_Masilela_402110604.smartpantrymanager.activities.SuggestedRecipesActivity;
import com.Siya_Masilela_402110604.smartpantrymanager.adapters.PantryAdapter;
import com.Siya_Masilela_402110604.smartpantrymanager.database.DatabaseHelper;
import com.Siya_Masilela_402110604.smartpantrymanager.models.PantryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPantry;
    private PantryAdapter pantryAdapter;
    private DatabaseHelper databaseHelper;
    private List<PantryItem> pantryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Pantry");

        recyclerViewPantry = findViewById(R.id.recyclerViewPantry);
        recyclerViewPantry.setLayoutManager(new LinearLayoutManager(this));
        loadPantryItems();

        FloatingActionButton fabAddItem = findViewById(R.id.fabAddItem);
        fabAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            intent.putExtra("mode", "add");
            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_pantry);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_pantry) {
                return true;
            } else if (itemId == R.id.nav_recipes) {
                startActivity(new Intent(MainActivity.this, SuggestedRecipesActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void loadPantryItems() {
        pantryItems = databaseHelper.getAllPantryItems();
        if (pantryAdapter == null) {
            pantryAdapter = new PantryAdapter(pantryItems, new PantryAdapter.OnPantryItemClickListener() {
                @Override
                public void onEditClick(PantryItem item) {
                    Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
                    intent.putExtra("mode", "edit");
                    intent.putExtra("item_id", item.getId());
                    intent.putExtra("item_name", item.getName());
                    intent.putExtra("item_quantity", item.getQuantity());
                    intent.putExtra("item_unit", item.getUnit());
                    intent.putExtra("item_expiry", item.getExpiryDate());
                    startActivity(intent);
                }

                @Override
                public void onDeleteClick(PantryItem item) {
                    databaseHelper.deletePantryItem(item.getId());
                    loadPantryItems();
                    Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                }
            });
            recyclerViewPantry.setAdapter(pantryAdapter);
        } else {
            pantryAdapter.updateItems(pantryItems);
        }
    }
}
