package dev.fantasma.recipeSharingApp.dtos.requests;

import dev.fantasma.recipeSharingApp.data.models.Category;
import lombok.Data;

import java.util.List;

@Data
public class AddRecipeRequest {
    private String name;
    private String description;
    private String image;
    private List<String> ingredients;
    private List<String> instructions;
    private Category category;
    private int preparationTime;
}
