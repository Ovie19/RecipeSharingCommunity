package dev.fantasma.recipeSharingApp.services;

import dev.fantasma.recipeSharingApp.data.models.Category;
import dev.fantasma.recipeSharingApp.data.models.User;
import dev.fantasma.recipeSharingApp.data.repositories.UserRepository;
import dev.fantasma.recipeSharingApp.dtos.requests.AddRecipeRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.RecipeResponse;
import dev.fantasma.recipeSharingApp.exceptions.RecipeAppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecipeServiceIntegrationTest {

    @Autowired private UserRepository userRepository;
    @Autowired private RecipeService recipeService;

    private AddRecipeRequest addRecipeRequest;

    @BeforeEach
    void setUp() {
        addRecipeRequest = new AddRecipeRequest();
        addRecipeRequest.setName("testRecipe");
        addRecipeRequest.setDescription("This is a test recipe");
        addRecipeRequest.setImage("test-image-url.png");
        addRecipeRequest.setIngredients(List.of("Ingredient 1", "Ingredient 2"));
        addRecipeRequest.setInstructions(List.of("Step 1: Prep", "Step 2: Cook"));
        addRecipeRequest.setCategory(Category.LUNCH);
        addRecipeRequest.setPreparationTime(30);
    }

    @Test
    void addRecipeIsSuccessfulTest() throws RecipeAppException {
        User testUser = createTestUser();
        testUser.setLoggedIn(true);
        userRepository.save(testUser);

        RecipeResponse response = recipeService.addRecipe(testUser.getId(), addRecipeRequest);
        assertEquals(addRecipeRequest.getName(), response.getName());
        assertEquals(addRecipeRequest.getDescription(), response.getDescription());
        assertEquals(addRecipeRequest.getImage(), response.getImage());
        assertEquals(addRecipeRequest.getIngredients(), response.getIngredients());
        assertEquals(addRecipeRequest.getInstructions(), response.getInstructions());
        assertEquals(addRecipeRequest.getCategory(), response.getCategory());
        assertEquals(addRecipeRequest.getPreparationTime(), response.getPreparationTime());
    }

    @Test
    void addRecipe_userNotLoggedInThrowsExceptionTest() {
        User testUser = createTestUser();
        assertThrows(
                RecipeAppException.class,
                () -> recipeService.addRecipe(testUser.getId(), addRecipeRequest)
        );
    }

    private User createTestUser() {
        User user = new User();
        user.setUsername("test-user");
        user.setPassword("test-password");
        return userRepository.save(user);
    }
}
