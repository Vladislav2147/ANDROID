package by.bstu.svs.stpms.myrecipes;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import by.bstu.svs.stpms.myrecipes.manager.DatabaseRecipeManager;
import by.bstu.svs.stpms.myrecipes.manager.ImageManager;
import by.bstu.svs.stpms.myrecipes.manager.exception.SQLiteDatabaseException;
import by.bstu.svs.stpms.myrecipes.model.Recipe;

public class RecipeShowActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_show);

        Integer recipeId = (Integer) getIntent().getSerializableExtra("recipeId");
        try {
            recipe = DatabaseRecipeManager.getInstance(this).getById(recipeId);
            showRecipe(recipe);
        } catch (SQLiteDatabaseException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showRecipe(Recipe recipe) {
        TextView titleTextView = findViewById(R.id.title);
        TextView categoryTextView = findViewById(R.id.category);
        TextView timeToCookTextView = findViewById(R.id.time_to_cook);
        TextView ingredientsTextView = findViewById(R.id.ingredients);
        TextView stepsTextView = findViewById(R.id.steps);
        ImageView imageView = findViewById(R.id.image);

        titleTextView.setText(recipe.getTitle());
        categoryTextView.setText(recipe.getCategory().getName());
        timeToCookTextView.setText(recipe.getTimeToCook().toString());
        ingredientsTextView.setText(recipe.getIngredients());
        stepsTextView.setText(recipe.getSteps());

        if (recipe.getPicture() != null) {
            File image = new File(super.getFilesDir().getAbsolutePath() + "/images", recipe.getPicture());
            ImageManager.getBitMapFromFile(image).ifPresent(imageView::setImageBitmap);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_recipe_action_menu, menu);
        MenuItem item = menu.findItem(R.id.favorite);
        if (!recipe.isFavorite()) {
            item.setIcon(R.drawable.ic_baseline_star_border_24);
        } else if (recipe.isFavorite()) {
            item.setIcon(R.drawable.ic_baseline_star_24);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.favorite:
                if (recipe.isFavorite()) {
                    recipe.setFavorite(false);
                    item.setIcon(R.drawable.ic_baseline_star_border_24);
                } else if (!recipe.isFavorite()) {
                    recipe.setFavorite(true);
                    item.setIcon(R.drawable.ic_baseline_star_24);
                }
        }

        return true;

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            DatabaseRecipeManager.getInstance(this).update(recipe);
        } catch (SQLiteDatabaseException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}