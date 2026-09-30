package com.Siya_Masilela_402110604.smartpantrymanager;

import com.Siya_Masilela_402110604.smartpantrymanager.models.PantryItem;
import com.Siya_Masilela_402110604.smartpantrymanager.models.Recipe;
import com.Siya_Masilela_402110604.smartpantrymanager.utils.StrictMatchingLogic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StrictMatchingLogicTest {

    private Recipe omelette() {
        Map<String, Double> ingredients = new HashMap<>();
        ingredients.put("eggs", 3.0);
        ingredients.put("butter", 1.0);
        return new Recipe(1, "Classic Omelette", ingredients, Collections.singletonList("Cook"));
    }

    private List<Recipe> matches(PantryItem... items) {
        return StrictMatchingLogic.getStrictMatches(
                Collections.singletonList(omelette()), new ArrayList<>(Arrays.asList(items)));
    }

    @Test
    public void matchesWhenAllIngredientsAvailable() {
        assertEquals(1, matches(
                new PantryItem("eggs", 6, "pcs", ""),
                new PantryItem("butter", 1, "tbsp", "")).size());
    }

    @Test
    public void skipsRecipeWhenIngredientMissing() {
        assertTrue(matches(new PantryItem("eggs", 6, "pcs", "")).isEmpty());
    }

    @Test
    public void skipsRecipeWhenQuantityInsufficient() {
        assertTrue(matches(
                new PantryItem("eggs", 2, "pcs", ""),
                new PantryItem("butter", 1, "tbsp", "")).isEmpty());
    }

    @Test
    public void matchIsCaseInsensitiveAndTrimmed() {
        assertEquals(1, matches(
                new PantryItem("  Eggs ", 3, "pcs", ""),
                new PantryItem("BUTTER", 1, "tbsp", "")).size());
    }

    @Test
    public void matchesSingularAgainstPlural() {
        assertEquals(1, matches(
                new PantryItem("egg", 3, "pcs", ""),
                new PantryItem("butter", 1, "tbsp", "")).size());
    }

    @Test
    public void sumsDuplicatePantryEntries() {
        assertEquals(1, matches(
                new PantryItem("eggs", 2, "pcs", ""),
                new PantryItem("eggs", 2, "pcs", ""),
                new PantryItem("butter", 1, "tbsp", "")).size());
    }
}
