package dev.fantasma.recipeSharingApp.utils;

import dev.fantasma.recipeSharingApp.data.models.Recipe;
import dev.fantasma.recipeSharingApp.dtos.requests.AddRecipeRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.RecipeResponse;

public class Mapper {

    public static Recipe map(AddRecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.setName(request.getName());
        recipe.setDescription(request.getDescription());
        recipe.setImage(request.getImage());
        recipe.setPreparationTime(request.getPreparationTime());
        recipe.setIngredients(request.getIngredients());
        recipe.setInstructions(request.getInstructions());
        recipe.setCategory(request.getCategory());
        return recipe;
    }

    public static RecipeResponse map(Recipe recipe) {
        RecipeResponse response = new RecipeResponse();
        response.setId(recipe.getId());
        response.setName(recipe.getName());
        response.setDescription(recipe.getDescription());
        response.setImage(recipe.getImage());
        response.setPreparationTime(recipe.getPreparationTime());
        response.setIngredients(recipe.getIngredients());
        response.setInstructions(recipe.getInstructions());
        response.setCategory(recipe.getCategory());
        return response;
    }
}
