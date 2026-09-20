package com.Siya_Masilela_402110604.smartpantrymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.Siya_Masilela_402110604.smartpantrymanager.models.PantryItem;
import com.Siya_Masilela_402110604.smartpantrymanager.models.Recipe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PANTRY = "pantry";
    private static final String TABLE_RECIPES = "recipes";
    private static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_UNIT = "unit";
    private static final String COLUMN_EXPIRY = "expiry_date";
    private static final String COLUMN_RECIPE_ID = "recipe_id";
    private static final String COLUMN_RECIPE_NAME = "recipe_name";
    private static final String COLUMN_STEPS = "steps";
    private static final String COLUMN_RECIPE_FK = "recipe_fk";
    private static final String COLUMN_INGREDIENT_NAME = "ingredient_name";
    private static final String COLUMN_INGREDIENT_QUANTITY = "ingredient_quantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPantryTable = "CREATE TABLE " + TABLE_PANTRY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_QUANTITY + " REAL NOT NULL, "
                + COLUMN_UNIT + " TEXT, "
                + COLUMN_EXPIRY + " TEXT" + ")";
        db.execSQL(createPantryTable);

        String createRecipesTable = "CREATE TABLE " + TABLE_RECIPES + "("
                + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RECIPE_NAME + " TEXT NOT NULL, "
                + COLUMN_STEPS + " TEXT" + ")";
        db.execSQL(createRecipesTable);

        String createRecipeIngredientsTable = "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + "("
                + COLUMN_RECIPE_FK + " INTEGER NOT NULL, "
                + COLUMN_INGREDIENT_NAME + " TEXT NOT NULL, "
                + COLUMN_INGREDIENT_QUANTITY + " REAL NOT NULL, "
                + "FOREIGN KEY(" + COLUMN_RECIPE_FK + ") REFERENCES " + TABLE_RECIPES + "(" + COLUMN_RECIPE_ID + "), "
                + "PRIMARY KEY(" + COLUMN_RECIPE_FK + ", " + COLUMN_INGREDIENT_NAME + ")" + ")";
        db.execSQL(createRecipeIngredientsTable);

        insertSeedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        onCreate(db);
    }

    private void insertSeedRecipes(SQLiteDatabase db) {
        long r1 = db.insert(TABLE_RECIPES, null, createRecipeValues("Simple Pasta", "1. Boil pasta\n2. Heat sauce\n3. Combine and serve"));
        insertRecipeIngredient(db, r1, "pasta", 200);
        insertRecipeIngredient(db, r1, "tomato sauce", 1);
        insertRecipeIngredient(db, r1, "garlic", 2);
        insertRecipeIngredient(db, r1, "olive oil", 2);

        long r2 = db.insert(TABLE_RECIPES, null, createRecipeValues("Classic Omelette", "1. Beat eggs\n2. Cook in butter\n3. Fold and serve"));
        insertRecipeIngredient(db, r2, "eggs", 3);
        insertRecipeIngredient(db, r2, "butter", 1);
        insertRecipeIngredient(db, r2, "salt", 1);
        insertRecipeIngredient(db, r2, "pepper", 1);

        long r3 = db.insert(TABLE_RECIPES, null, createRecipeValues("Simple Salad", "1. Chop vegetables\n2. Toss with dressing\n3. Serve"));
        insertRecipeIngredient(db, r3, "lettuce", 1);
        insertRecipeIngredient(db, r3, "tomato", 2);
        insertRecipeIngredient(db, r3, "cucumber", 1);
        insertRecipeIngredient(db, r3, "olive oil", 2);
        insertRecipeIngredient(db, r3, "vinegar", 1);

        long r4 = db.insert(TABLE_RECIPES, null, createRecipeValues("Grilled Cheese", "1. Butter bread\n2. Add cheese\n3. Grill until golden"));
        insertRecipeIngredient(db, r4, "bread", 2);
        insertRecipeIngredient(db, r4, "cheese", 2);
        insertRecipeIngredient(db, r4, "butter", 1);

        long r5 = db.insert(TABLE_RECIPES, null, createRecipeValues("Fruit Smoothie", "1. Add to blender\n2. Blend\n3. Serve"));
        insertRecipeIngredient(db, r5, "banana", 1);
        insertRecipeIngredient(db, r5, "milk", 1);
        insertRecipeIngredient(db, r5, "yogurt", 0.5);
        insertRecipeIngredient(db, r5, "honey", 1);

        long r6 = db.insert(TABLE_RECIPES, null, createRecipeValues("Tomato Soup", "1. Saute onions\n2. Add tomatoes\n3. Simmer and blend"));
        insertRecipeIngredient(db, r6, "tomato", 6);
        insertRecipeIngredient(db, r6, "onion", 1);
        insertRecipeIngredient(db, r6, "garlic", 2);
        insertRecipeIngredient(db, r6, "broth", 2);

        long r7 = db.insert(TABLE_RECIPES, null, createRecipeValues("French Toast", "1. Beat eggs and milk\n2. Dip bread\n3. Cook until golden"));
        insertRecipeIngredient(db, r7, "bread", 4);
        insertRecipeIngredient(db, r7, "eggs", 2);
        insertRecipeIngredient(db, r7, "milk", 0.5);
        insertRecipeIngredient(db, r7, "cinnamon", 0.5);

        long r8 = db.insert(TABLE_RECIPES, null, createRecipeValues("Stir Fry", "1. Heat oil\n2. Add vegetables\n3. Add soy sauce"));
        insertRecipeIngredient(db, r8, "broccoli", 1);
        insertRecipeIngredient(db, r8, "carrot", 2);
        insertRecipeIngredient(db, r8, "bell pepper", 1);
        insertRecipeIngredient(db, r8, "soy sauce", 2);
        insertRecipeIngredient(db, r8, "oil", 1);

        long r9 = db.insert(TABLE_RECIPES, null, createRecipeValues("Scrambled Eggs", "1. Beat eggs\n2. Cook in butter\n3. Stir constantly"));
        insertRecipeIngredient(db, r9, "eggs", 3);
        insertRecipeIngredient(db, r9, "butter", 1);
        insertRecipeIngredient(db, r9, "salt", 1);
        insertRecipeIngredient(db, r9, "pepper", 1);

        long r10 = db.insert(TABLE_RECIPES, null, createRecipeValues("Pancakes", "1. Mix dry\n2. Add wet\n3. Cook on griddle"));
        insertRecipeIngredient(db, r10, "flour", 200);
        insertRecipeIngredient(db, r10, "eggs", 1);
        insertRecipeIngredient(db, r10, "milk", 1);
        insertRecipeIngredient(db, r10, "sugar", 2);

        long r11 = db.insert(TABLE_RECIPES, null, createRecipeValues("Guacamole", "1. Mash avocado\n2. Add onion and tomato\n3. Season"));
        insertRecipeIngredient(db, r11, "avocado", 2);
        insertRecipeIngredient(db, r11, "onion", 0.5);
        insertRecipeIngredient(db, r11, "tomato", 1);
        insertRecipeIngredient(db, r11, "lime juice", 1);

        long r12 = db.insert(TABLE_RECIPES, null, createRecipeValues("Caprese Salad", "1. Slice tomatoes and mozzarella\n2. Add basil\n3. Drizzle oil"));
        insertRecipeIngredient(db, r12, "tomato", 2);
        insertRecipeIngredient(db, r12, "mozzarella", 200);
        insertRecipeIngredient(db, r12, "basil", 10);
        insertRecipeIngredient(db, r12, "olive oil", 2);

        long r13 = db.insert(TABLE_RECIPES, null, createRecipeValues("Garlic Bread", "1. Mix butter and garlic\n2. Spread on bread\n3. Toast"));
        insertRecipeIngredient(db, r13, "bread", 4);
        insertRecipeIngredient(db, r13, "butter", 4);
        insertRecipeIngredient(db, r13, "garlic", 3);

        long r14 = db.insert(TABLE_RECIPES, null, createRecipeValues("Yogurt Parfait", "1. Layer yogurt and fruit\n2. Top with honey"));
        insertRecipeIngredient(db, r14, "yogurt", 1);
        insertRecipeIngredient(db, r14, "berries", 1);
        insertRecipeIngredient(db, r14, "honey", 1);

        long r15 = db.insert(TABLE_RECIPES, null, createRecipeValues("Iced Tea", "1. Brew tea\n2. Add sugar and lemon\n3. Chill"));
        insertRecipeIngredient(db, r15, "tea", 1);
        insertRecipeIngredient(db, r15, "sugar", 2);
        insertRecipeIngredient(db, r15, "lemon", 1);

        long r16 = db.insert(TABLE_RECIPES, null, createRecipeValues("Oatmeal", "1. Boil milk\n2. Add oats\n3. Top with fruit"));
        insertRecipeIngredient(db, r16, "oats", 0.5);
        insertRecipeIngredient(db, r16, "milk", 1);
        insertRecipeIngredient(db, r16, "cinnamon", 0.5);

        long r17 = db.insert(TABLE_RECIPES, null, createRecipeValues("Chicken Salad", "1. Mix chicken and mayo\n2. Season\n3. Serve"));
        insertRecipeIngredient(db, r17, "chicken", 200);
        insertRecipeIngredient(db, r17, "mayo", 2);
        insertRecipeIngredient(db, r17, "salt", 1);
        insertRecipeIngredient(db, r17, "pepper", 1);

        long r18 = db.insert(TABLE_RECIPES, null, createRecipeValues("Tuna Salad", "1. Mix tuna and mayo\n2. Season\n3. Serve"));
        insertRecipeIngredient(db, r18, "tuna", 1);
        insertRecipeIngredient(db, r18, "mayo", 2);
        insertRecipeIngredient(db, r18, "salt", 1);
        insertRecipeIngredient(db, r18, "pepper", 1);
    }

    private ContentValues createRecipeValues(String name, String steps) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_NAME, name);
        values.put(COLUMN_STEPS, steps);
        return values;
    }

    private void insertRecipeIngredient(SQLiteDatabase db, long recipeId, String ingredient, double quantity) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_FK, recipeId);
        values.put(COLUMN_INGREDIENT_NAME, ingredient.toLowerCase().trim());
        values.put(COLUMN_INGREDIENT_QUANTITY, quantity);
        db.insert(TABLE_RECIPE_INGREDIENTS, null, values);
    }

    public long addPantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName().toLowerCase().trim());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_UNIT, item.getUnit());
        values.put(COLUMN_EXPIRY, item.getExpiryDate());
        return db.insert(TABLE_PANTRY, null, values);
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PANTRY, null, null, null, null, null, COLUMN_NAME + " ASC");
        if (cursor.moveToFirst()) {
            do {
                PantryItem item = new PantryItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY))
                );
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName().toLowerCase().trim());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_UNIT, item.getUnit());
        values.put(COLUMN_EXPIRY, item.getExpiryDate());
        return db.update(TABLE_PANTRY, values, COLUMN_ID + " = ?", new String[]{String.valueOf(item.getId())});
    }

    public void deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PANTRY, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int recipeId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME));
                String stepsStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STEPS));
                List<String> steps = new ArrayList<>();
                for (String step : stepsStr.split("\n")) steps.add(step);
                Map<String, Double> ingredients = getRecipeIngredients(recipeId);
                recipes.add(new Recipe(recipeId, name, ingredients, steps));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return recipes;
    }

    private Map<String, Double> getRecipeIngredients(int recipeId) {
        Map<String, Double> ingredients = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPE_INGREDIENTS,
                new String[]{COLUMN_INGREDIENT_NAME, COLUMN_INGREDIENT_QUANTITY},
                COLUMN_RECIPE_FK + " = ?", new String[]{String.valueOf(recipeId)},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENT_NAME));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENT_QUANTITY));
                ingredients.put(name, quantity);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ingredients;
    }

    public Recipe getRecipeById(int recipeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, null, COLUMN_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME));
            String stepsStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STEPS));
            List<String> steps = new ArrayList<>();
            for (String step : stepsStr.split("\n")) steps.add(step);
            Map<String, Double> ingredients = getRecipeIngredients(recipeId);
            cursor.close();
            return new Recipe(recipeId, name, ingredients, steps);
        }
        cursor.close();
        return null;
    }
}
