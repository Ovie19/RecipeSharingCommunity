package dev.fantasma.recipeSharingApp.services;

import dev.fantasma.recipeSharingApp.dtos.requests.AddRecipeRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.RecipeResponse;
import dev.fantasma.recipeSharingApp.exceptions.RecipeAppException;

import java.util.List;

public interface RecipeService {
    RecipeResponse addRecipe(Long userId, AddRecipeRequest request) throws RecipeAppException;
    List<RecipeResponse> getAllRecipe();
    void getAllUserRecipe(Long userId);
}
