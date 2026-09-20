package com.Siya_Masilela_402110604.smartpantrymanager.utils;

import com.Siya_Masilela_402110604.smartpantrymanager.models.PantryItem;
import com.Siya_Masilela_402110604.smartpantrymanager.models.Recipe;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StrictMatchingLogic {

    public static List<Recipe> getStrictMatches(List<Recipe> allRecipes, List<PantryItem> pantryItems) {
        List<Recipe> matchingRecipes = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            if (canMakeRecipe(recipe, pantryItems)) {
                matchingRecipes.add(recipe);
            }
        }
        return matchingRecipes;
    }

    private static boolean canMakeRecipe(Recipe recipe, List<PantryItem> pantryItems) {
        Map<String, Double> requiredIngredients = recipe.getRequiredIngredients();
        for (Map.Entry<String, Double> required : requiredIngredients.entrySet()) {
            String requiredName = required.getKey().toLowerCase().trim();
            double requiredQuantity = required.getValue();
            if (!hasIngredient(pantryItems, requiredName, requiredQuantity)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hasIngredient(List<PantryItem> pantryItems, String requiredName, double requiredQuantity) {
        for (PantryItem item : pantryItems) {
            String pantryName = item.getName().toLowerCase().trim();
            if (pantryName.equals(requiredName)) {
                return item.getQuantity() >= requiredQuantity;
            }
            if (isPluralMatch(pantryName, requiredName) || isPluralMatch(requiredName, pantryName)) {
                return item.getQuantity() >= requiredQuantity;
            }
        }
        return false;
    }

    private static boolean isPluralMatch(String word1, String word2) {
        String word1Singular = word1;
        if (word1.endsWith("es") && !word1.endsWith("ss") && !word1.endsWith("shes")) {
            word1Singular = word1.substring(0, word1.length() - 2);
        } else if (word1.endsWith("s") && !word1.endsWith("ss") && !word1.endsWith("us")) {
            word1Singular = word1.substring(0, word1.length() - 1);
        }
        return word1Singular.equals(word2) || word1Singular.equals(word2 + "e");
    }
}
