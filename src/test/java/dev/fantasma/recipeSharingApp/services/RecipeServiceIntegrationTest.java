package dev.fantasma.recipeSharingApp.services;

import dev.fantasma.recipeSharingApp.data.repositories.RecipeRepository;
import dev.fantasma.recipeSharingApp.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
public class RecipeServiceIntegrationTest {

    @Autowired private RecipeRepository recipeRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RecipeService recipeService;


}
