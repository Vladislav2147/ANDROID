package by.bstu.svs.stpms.myrecipes;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import by.bstu.svs.stpms.myrecipes.manager.ImageManager;
import by.bstu.svs.stpms.myrecipes.model.Recipe;

public class RecipeShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_show);

        Long recipeId = (Long) getIntent().getSerializableExtra("recipeId");
        String userUid = getIntent().getStringExtra("user");
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference();

                ref.child(userUid)
                .orderByChild("id")
                .equalTo(recipeId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot recipeKey: snapshot.getChildren()) {
                            Recipe recipe = recipeKey.getValue(Recipe.class);
                            showRecipe(recipe);
                            break;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });


    }


    private void showRecipe(Recipe recipe) {
        TextView titleTextView = findViewById(R.id.title);
        TextView categoryTextView = findViewById(R.id.category);
        TextView timeToCookTextView = findViewById(R.id.time_to_cook);
        TextView ingredientsTextView = findViewById(R.id.ingredients);
        TextView stepsTextView = findViewById(R.id.steps);
        ImageView imageView = findViewById(R.id.image);

        titleTextView.setText(recipe.getTitle());
        categoryTextView.setText(recipe.getCategory().toString());
        //timeToCookTextView.setText(recipe.getTimeToCook().toString());
        ingredientsTextView.setText(recipe.getIngredients());
        stepsTextView.setText(recipe.getSteps());

        if (recipe.getPicture() != null) {
            File image = new File(super.getFilesDir().getAbsolutePath() + "/images", recipe.getPicture());
            ImageManager.getBitMapFromFile(image).ifPresent(imageView::setImageBitmap);
        }

    }

}