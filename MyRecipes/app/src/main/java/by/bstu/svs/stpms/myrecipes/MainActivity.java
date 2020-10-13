package by.bstu.svs.stpms.myrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import by.bstu.svs.stpms.myrecipes.manager.JsonManager;
import by.bstu.svs.stpms.myrecipes.model.CookingBook;
import by.bstu.svs.stpms.myrecipes.recycler.RecipeAdapter;

//TODO menus
public class MainActivity extends AppCompatActivity {

    private static final String json = "cooking_book.json";
    private RecyclerView recipesRecyclerView;
    private RecipeAdapter recipeAdapter;
    private JsonManager jsonManager;
    private CookingBook cookingBook;
    private Intent recipeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeIntent = new Intent(this, RecipeShowActivity.class);
        jsonManager = new JsonManager(new File(super.getFilesDir(), json));
        cookingBook = jsonManager.getFromFile().orElse(new CookingBook());
        initRecycleView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recipeAdapter.setBook(jsonManager.getFromFile().orElse(new CookingBook()));
    }

    public void createRecipe(View view) {
        Intent recipeCreateIntent = new Intent(this, RecipeCreateActivity.class);
        startActivity(recipeCreateIntent);
    }

    public void initRecycleView() {

        recipeAdapter = new RecipeAdapter(cookingBook, super.getFilesDir());
        recipeAdapter.setListener(recipe -> {
            recipeIntent.putExtra("recipe", recipe);
            startActivity(recipeIntent);
        });

        recipesRecyclerView = findViewById(R.id.recipe_recycler_view);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipesRecyclerView.setAdapter(recipeAdapter);

    }
}