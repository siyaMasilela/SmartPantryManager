package com.Siya_Masilela_402110604.smartpantrymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.Siya_Masilela_402110604.smartpantrymanager.R;
import com.Siya_Masilela_402110604.smartpantrymanager.adapters.RecipeAdapter;
import com.Siya_Masilela_402110604.smartpantrymanager.database.DatabaseHelper;
import com.Siya_Masilela_402110604.smartpantrymanager.models.PantryItem;
import com.Siya_Masilela_402110604.smartpantrymanager.models.Recipe;
import com.Siya_Masilela_402110604.smartpantrymanager.utils.StrictMatchingLogic;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRecipes;
    private TextView tvNoRecipes, tvRecipeCount;
    private RecipeAdapter recipeAdapter;
    private DatabaseHelper databaseHelper;
    private List<Recipe> suggestedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);
        databaseHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbarRecipes);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Suggested Recipes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewRecipes = findViewById(R.id.recyclerViewRecipes);
        tvNoRecipes = findViewById(R.id.tvNoRecipes);
        tvRecipeCount = findViewById(R.id.tvRecipeCount);
        recyclerViewRecipes.setLayoutManager(new LinearLayoutManager(this));

        loadSuggestedRecipes();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationRecipes);
        bottomNavigationView.setSelectedItemId(R.id.nav_recipes);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_pantry) {
                startActivity(new Intent(SuggestedRecipesActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_recipes) {
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(SuggestedRecipesActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    private void loadSuggestedRecipes() {
        List<Recipe> allRecipes = databaseHelper.getAllRecipes();
        List<PantryItem> pantryItems = databaseHelper.getAllPantryItems();
        suggestedRecipes = StrictMatchingLogic.getStrictMatches(allRecipes, pantryItems);
        tvRecipeCount.setText("Found " + suggestedRecipes.size() + " recipes you can make");

        if (suggestedRecipes.isEmpty()) {
            tvNoRecipes.setVisibility(View.VISIBLE);
            recyclerViewRecipes.setVisibility(View.GONE);
        } else {
            tvNoRecipes.setVisibility(View.GONE);
            recyclerViewRecipes.setVisibility(View.VISIBLE);
            if (recipeAdapter == null) {
                recipeAdapter = new RecipeAdapter(this, suggestedRecipes, recipe -> {
                    Intent intent = new Intent(SuggestedRecipesActivity.this, RecipeDetailActivity.class);
                    intent.putExtra("recipe_id", recipe.getId());
                    intent.putExtra("recipe_name", recipe.getName());
                    intent.putStringArrayListExtra("recipe_ingredients", new java.util.ArrayList<>(recipe.getRequiredIngredients().keySet()));
                    intent.putStringArrayListExtra("recipe_steps", new java.util.ArrayList<>(recipe.getPreparationSteps()));
                    startActivity(intent);
                });
                recyclerViewRecipes.setAdapter(recipeAdapter);
            } else {
                recipeAdapter.updateRecipes(suggestedRecipes);
            }
        }
    }

    @Override
    protected void onResume() { super.onResume(); loadSuggestedRecipes(); }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { finish(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
