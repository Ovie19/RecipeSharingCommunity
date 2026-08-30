package dev.fantasma.recipeSharingApp.controllers;

import dev.fantasma.recipeSharingApp.dtos.requests.AddRecipeRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.ApiResponse;
import dev.fantasma.recipeSharingApp.dtos.responses.RecipeResponse;
import dev.fantasma.recipeSharingApp.exceptions.RecipeAppException;
import dev.fantasma.recipeSharingApp.services.RecipeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {

    private RecipeService recipeService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createRecipe(
            @PathVariable Long userId,
            @RequestBody AddRecipeRequest addRecipeRequest
    ) {
        try {
            RecipeResponse response = recipeService.addRecipe(userId, addRecipeRequest);
            return new ResponseEntity<>(new ApiResponse(response, true), CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRecipe() {
        List<RecipeResponse> responseList = recipeService.getAllRecipe();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
