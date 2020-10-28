package by.bstu.svs.stpms.myrecipes;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import by.bstu.svs.stpms.myrecipes.manager.FirebaseManager;
import by.bstu.svs.stpms.myrecipes.manager.ImageManager;
import by.bstu.svs.stpms.myrecipes.model.Recipe;

public class RecipeShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_show);

        String  recipeId = (String) getIntent().getSerializableExtra("recipeId");
        FirebaseManager.getInstance().callOnRecipeById(recipeId, this::showRecipe);
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

}