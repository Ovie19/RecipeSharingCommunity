package dev.fantasma.recipeSharingApp.dtos.responses;

import dev.fantasma.recipeSharingApp.data.models.Category;
import lombok.Data;

import java.util.List;

@Data
public class RecipeResponse {
    private Long id;
    private String name;
    private String description;
    private String image;
    private List<String> ingredients;
    private List<String> instructions;
    private Category category;
    private int preparationTime;
}
