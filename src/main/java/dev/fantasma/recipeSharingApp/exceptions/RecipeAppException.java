package dev.fantasma.recipeSharingApp.exceptions;

public class RecipeAppException extends Exception {
    public RecipeAppException(String message) {
        super(message);
    }

    public RecipeAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecipeAppException(Throwable cause) {
        super(cause);
    }
}
