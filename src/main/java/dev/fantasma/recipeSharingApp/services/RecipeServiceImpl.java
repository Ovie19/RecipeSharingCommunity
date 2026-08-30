package dev.fantasma.recipeSharingApp.services;

import dev.fantasma.recipeSharingApp.data.models.Recipe;
import dev.fantasma.recipeSharingApp.data.models.User;
import dev.fantasma.recipeSharingApp.data.repositories.RecipeRepository;
import dev.fantasma.recipeSharingApp.data.repositories.UserRepository;
import dev.fantasma.recipeSharingApp.dtos.requests.AddRecipeRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.RecipeResponse;
import dev.fantasma.recipeSharingApp.exceptions.RecipeAppException;
import dev.fantasma.recipeSharingApp.utils.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.fantasma.recipeSharingApp.utils.Mapper.map;
import static dev.fantasma.recipeSharingApp.utils.Validator.validate;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    private UserRepository userRepository;

    @Override
    public RecipeResponse addRecipe(Long userId, AddRecipeRequest request) throws RecipeAppException {
        validate(request);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RecipeAppException("User not found"));

        if (!user.isLoggedIn()) throw new RecipeAppException("User is not logged in");

        Recipe recipe = map(request);
        recipe.setAuthor(user);
        return map(recipeRepository.save(recipe));
    }

    @Override
    public List<RecipeResponse> getAllRecipe() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(Mapper::map).toList();
    }

    @Override
    public void getAllUserRecipe(Long userId) {

    }
}
