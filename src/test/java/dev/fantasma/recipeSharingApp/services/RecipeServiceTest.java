package dev.fantasma.recipeSharingApp.services;

import dev.fantasma.recipeSharingApp.data.models.Category;
import dev.fantasma.recipeSharingApp.data.models.Recipe;
import dev.fantasma.recipeSharingApp.data.models.User;
import dev.fantasma.recipeSharingApp.data.repositories.RecipeRepository;
import dev.fantasma.recipeSharingApp.data.repositories.UserRepository;
import dev.fantasma.recipeSharingApp.dtos.requests.AddRecipeRequest;
import dev.fantasma.recipeSharingApp.dtos.responses.RecipeResponse;
import dev.fantasma.recipeSharingApp.exceptions.RecipeAppException;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private final Long testUserId = 1L;

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
    void addRecipe_nullRequestThrowsExceptionTest() {
        assertThrows(RecipeAppException.class, () -> recipeService.addRecipe(testUserId, null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void addRecipe_invalidRecipeNameThrowsExceptionTest(String recipeName) {
        addRecipeRequest.setName(recipeName);
        assertThrows(RecipeAppException.class, () -> recipeService.addRecipe(testUserId, addRecipeRequest));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void addRecipe_invalidRecipeDescriptionThrowsExceptionTest(String description) {
        addRecipeRequest.setDescription(description);
        assertThrows(RecipeAppException.class, () -> recipeService.addRecipe(testUserId, addRecipeRequest));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void addRecipe_invalidRecipeImageThrowsExceptionTest(String image) {
        addRecipeRequest.setImage(image);
        assertThrows(RecipeAppException.class, () -> recipeService.addRecipe(testUserId, addRecipeRequest));
    }

    @Test
    void addRecipe_invalidRecipeCategoryThrowsExceptionTest() {
        addRecipeRequest.setCategory(null);
        assertThrows(RecipeAppException.class, () -> recipeService.addRecipe(testUserId, addRecipeRequest));
    }

    @Test
    void addRecipe_recipeIngredientsIsNullThrowsExceptionTest() {
        addRecipeRequest.setIngredients(null);
        assertThrows(RecipeAppException.class, () -> recipeService.addRecipe(testUserId, addRecipeRequest));
    }

    @Test
    void addRecipe_recipeIngredientIsEmptyThrowsExceptionTest() {
        addRecipeRequest.setIngredients(List.of());
        assertThrows(RecipeAppException.class, () -> recipeService.addRecipe(testUserId, addRecipeRequest));
    }

    @Test
    void addRecipe_recipeInstructionIsNullThrowsExceptionTest() {
        addRecipeRequest.setInstructions(null);
        assertThrows(RecipeAppException.class, () -> recipeService.addRecipe(testUserId, addRecipeRequest));
    }

    @Test
    void addRecipe_recipeInstructionIsEmptyThrowsExceptionTest() {
        addRecipeRequest.setInstructions(List.of());
        assertThrows(RecipeAppException.class, () -> recipeService.addRecipe(testUserId, addRecipeRequest));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void addRecipe_invalidRecipePreparationTimeThrowsExceptionTest(int preparationTime) {
        addRecipeRequest.setPreparationTime(preparationTime);
        assertThrows(RecipeAppException.class, () -> recipeService.addRecipe(testUserId, addRecipeRequest));
    }

    @Test
    void addRecipe_userNotFoundThrowsExceptionTest() {
        when(userRepository.findById(testUserId)).thenReturn(Optional.empty());
        assertThrows(RecipeAppException.class, () -> recipeService.addRecipe(testUserId, addRecipeRequest));
        verify(userRepository, times(1)).findById(testUserId);
    }

    @Test
    void addRecipe_userNotLoggedInThrowsExceptionTest() {
        when(userRepository.findById(testUserId)).thenReturn(Optional.of(new User()));
        RecipeAppException exception = assertThrows(
                RecipeAppException.class,
                () -> recipeService.addRecipe(testUserId, addRecipeRequest)
        );
        assertEquals("User is not logged in", exception.getMessage());
        verify(userRepository, times(1)).findById(testUserId);
    }

    @Test
    void addRecipeSuccessfullyTest() throws RecipeAppException {
        User testUser = new User();
        testUser.setId(testUserId);
        testUser.setLoggedIn(true);

        Recipe recipe = getRecipe();
        recipe.setAuthor(testUser);

        when(userRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        RecipeResponse response = recipeService.addRecipe(testUserId, addRecipeRequest);
        assertNotNull(response);
        assertEquals(101L, response.getId());
        assertEquals("testRecipe", response.getName());
        assertEquals("This is a test recipe", response.getDescription());
        assertEquals("test-image-url.png", response.getImage());
        assertEquals(List.of("Ingredient 1", "Ingredient 2"), response.getIngredients());
        assertEquals(List.of("Step 1: Prep", "Step 2: Cook"), response.getInstructions());
        assertEquals(Category.LUNCH, response.getCategory());
        assertEquals(30, response.getPreparationTime());
        verify(userRepository, times(1)).findById(testUserId);
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    private @NonNull Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(101L);
        recipe.setName("testRecipe");
        recipe.setDescription("This is a test recipe");
        recipe.setImage("test-image-url.png");
        recipe.setIngredients(List.of("Ingredient 1", "Ingredient 2"));
        recipe.setInstructions(List.of("Step 1: Prep", "Step 2: Cook"));
        recipe.setCategory(Category.LUNCH);
        recipe.setPreparationTime(30);
        return recipe;
    }

    @Test
    void getAllRecipe_databaseIsEmpty_returnEmptyListTest() {
        when(recipeRepository.findAll()).thenReturn(List.of());
        List<RecipeResponse> responseList = recipeService.getAllRecipe();
        assertTrue(responseList.isEmpty());
    }

    @Test
    void getAllRecipe_whenRecipesExist_returnsResponseListTest() {
        Recipe pastaRecipe = new Recipe();
        pastaRecipe.setId(1L);
        pastaRecipe.setName("Pasta");

        Recipe cakeRecipe = new Recipe();
        cakeRecipe.setId(2L);
        cakeRecipe.setName("Cake");

        when(recipeRepository.findAll()).thenReturn(List.of(pastaRecipe, cakeRecipe));
        List<RecipeResponse> responseList = recipeService.getAllRecipe();
        assertEquals(2, responseList.size());
        assertEquals(pastaRecipe.getName(), responseList.get(0).getName());
        assertEquals(cakeRecipe.getName(), responseList.get(1).getName());
    }
}