package dev.fantasma.recipeSharingApp.data.repositories;

import dev.fantasma.recipeSharingApp.data.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
