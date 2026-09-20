package com.Siya_Masilela_402110604.smartpantrymanager.models;

import java.util.List;
import java.util.Map;

public class Recipe {
    private int id;
    private String name;
    private Map<String, Double> requiredIngredients;
    private List<String> preparationSteps;

    public Recipe(int id, String name, Map<String, Double> requiredIngredients, List<String> preparationSteps) {
        this.id = id;
        this.name = name;
        this.requiredIngredients = requiredIngredients;
        this.preparationSteps = preparationSteps;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Map<String, Double> getRequiredIngredients() { return requiredIngredients; }
    public List<String> getPreparationSteps() { return preparationSteps; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRequiredIngredients(Map<String, Double> requiredIngredients) { this.requiredIngredients = requiredIngredients; }
    public void setPreparationSteps(List<String> preparationSteps) { this.preparationSteps = preparationSteps; }

    @Override
    public String toString() {
        return name + " (" + requiredIngredients.size() + " ingredients)";
    }
}
