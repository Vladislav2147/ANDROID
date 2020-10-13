package by.bstu.svs.stpms.myrecipes.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class CookingBookTest {

    CookingBook cookingBook;

    @Before
    public void init() {

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(1L, "raviolli"));
        recipes.add(new Recipe(2L, "pizza"));
        recipes.add(new Recipe(3L, "pasta"));
        cookingBook = new CookingBook(recipes);

    }

    @Test
    public void testAddWithoutId() {
        Recipe recipe = new Recipe();
        recipe.setTitle("pel'meni");
        cookingBook.addWithoutId(recipe);

        Assert.assertEquals(new Long(4L), recipe.getId());
        Assert.assertTrue(cookingBook.getRecipes().contains(recipe));

    }

    @Test
    public void testRemoveById() {
        long deletingId = 3L;
        cookingBook.removeById(deletingId);
        Assert.assertTrue(cookingBook.getRecipes().stream().noneMatch(recipe -> recipe.getId().equals(deletingId)));
    }

    @Test
    public void testGetById() {
        Long idToFind = 1L;
        Recipe recipe = cookingBook.getById(idToFind);
        Assert.assertEquals(idToFind, recipe.getId());
    }

    @Test
    public void testUpdate() {
        String newTitle = "pineapple pizza";
        Recipe updateRecipe = cookingBook.getById(3L);
        updateRecipe.setTitle(newTitle);
        cookingBook.update(updateRecipe.getId(), updateRecipe);
        Assert.assertTrue(cookingBook.getById(3L).getTitle().equals(newTitle));
    }

    @Test
    public void testGetRecipes() {
        Assert.assertEquals(3, cookingBook.getRecipes().size());
    }
}