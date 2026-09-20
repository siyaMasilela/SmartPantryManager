package com.Siya_Masilela_402110604.smartpantrymanager.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.Siya_Masilela_402110604.smartpantrymanager.R;
import com.Siya_Masilela_402110604.smartpantrymanager.database.DatabaseHelper;
import com.Siya_Masilela_402110604.smartpantrymanager.models.Recipe;
import java.util.List;
import java.util.Map;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView tvRecipeName, tvIngredients, tvSteps;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        databaseHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbarRecipeDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvRecipeName = findViewById(R.id.tvRecipeNameDetail);
        tvIngredients = findViewById(R.id.tvIngredientsList);
        tvSteps = findViewById(R.id.tvStepsList);

        int recipeId = getIntent().getIntExtra("recipe_id", -1);
        String recipeName = getIntent().getStringExtra("recipe_name");

        if (recipeName != null) displayRecipeFromIntent();
        else if (recipeId != -1) displayRecipeFromDatabase(recipeId);
    }

    private void displayRecipeFromIntent() {
        String name = getIntent().getStringExtra("recipe_name");
        List<String> ingredients = getIntent().getStringArrayListExtra("recipe_ingredients");
        List<String> steps = getIntent().getStringArrayListExtra("recipe_steps");

        tvRecipeName.setText(name);
        tvRecipeName.setTextSize(24);

        StringBuilder it = new StringBuilder();
        if (ingredients != null) for (String i : ingredients) it.append("\u2022 ").append(i).append("\n");
        tvIngredients.setText(it.toString());

        StringBuilder st = new StringBuilder();
        if (steps != null) for (int i = 0; i < steps.size(); i++) st.append(i + 1).append(". ").append(steps.get(i)).append("\n\n");
        tvSteps.setText(st.toString());
    }

    private void displayRecipeFromDatabase(int recipeId) {
        Recipe recipe = databaseHelper.getRecipeById(recipeId);
        if (recipe != null) {
            tvRecipeName.setText(recipe.getName());
            tvRecipeName.setTextSize(24);

            StringBuilder it = new StringBuilder();
            for (Map.Entry<String, Double> e : recipe.getRequiredIngredients().entrySet())
                it.append("\u2022 ").append(e.getKey()).append(": ").append(e.getValue()).append("\n");
            tvIngredients.setText(it.toString());

            StringBuilder st = new StringBuilder();
            List<String> steps = recipe.getPreparationSteps();
            for (int i = 0; i < steps.size(); i++) st.append(i + 1).append(". ").append(steps.get(i)).append("\n\n");
            tvSteps.setText(st.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) { finish(); return true; }
        return super.onOptionsItemSelected(item);
    }
}
