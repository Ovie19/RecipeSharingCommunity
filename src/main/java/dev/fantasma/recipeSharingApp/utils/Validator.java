package dev.fantasma.recipeSharingApp.utils;

import dev.fantasma.recipeSharingApp.dtos.requests.AddRecipeRequest;
import dev.fantasma.recipeSharingApp.exceptions.RecipeAppException;

import java.util.List;

public class Validator {

    public static void validate(AddRecipeRequest request) throws RecipeAppException {
        if (request == null) throw new RecipeAppException("Request cannot be null");
        validateField(request.getName(), "Recipe Name");
        validateField(request.getDescription(), "Recipe Description");
        validateField(request.getImage(), "Recipe Image");
        validateField(request.getIngredients(), "Recipe Ingredients");
        validateField(request.getInstructions(), "Recipe Instructions");
        validateField(request.getPreparationTime(), "Preparation Time");
        if (request.getCategory() == null) throw new RecipeAppException("Recipe Category cannot be null");
    }

    public static void validateField(String value, String fieldName) throws RecipeAppException {
        if (value == null || value.isBlank()) throw new RecipeAppException("Invalid " + fieldName);
    }

    public static void validateField(List<String> value, String fieldName) throws RecipeAppException {
        if (value == null || value.isEmpty()) throw new RecipeAppException("Invalid " + fieldName);
    }

    public static void validateField(int value, String fieldName) throws RecipeAppException {
        if (value <= 0) throw new RecipeAppException("Invalid " + fieldName);
    }
}
