package by.bstu.svs.stpms.myrecipes.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Deprecated
public class CookingBook {

    private final List<Recipe> recipes;

    public CookingBook() {
        recipes = new ArrayList<>();
    }

//    public void addWithoutId(Recipe recipe) {
//        recipe.setId(0L);
//        recipes
//                .stream()
//                .max((recipe1, recipe2) -> recipe1.getId().compareTo(recipe2.getId()))
//                .ifPresent(lastRecipe -> {
//                    Long maxId = lastRecipe.getId();
//                    recipe.setId(maxId + 1);
//                });
//        recipes.add(recipe);
//    }

    public void removeById(Integer id) {
        recipes.removeIf(recipe -> recipe.getId().equals(id));
    }

    public Recipe getById(Integer id) {
        return recipes
                .stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public void update(Integer id, Recipe recipe) {
        removeById(id);
        recipe.setId(id);
        recipes.add(recipe);
    }

}
