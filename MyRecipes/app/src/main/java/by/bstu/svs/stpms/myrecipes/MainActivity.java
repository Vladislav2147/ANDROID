package by.bstu.svs.stpms.myrecipes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import by.bstu.svs.stpms.myrecipes.manager.JsonManager;
import by.bstu.svs.stpms.myrecipes.model.CookingBook;
import by.bstu.svs.stpms.myrecipes.model.Recipe;
import by.bstu.svs.stpms.myrecipes.recycler.RecipeAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String json = "cooking_book.json";
    private RecyclerView recipesRecyclerView;
    private RecipeAdapter recipeAdapter;
    private JsonManager jsonManager;
    private CookingBook cookingBook;
    private Intent recipeIntent;

    private List<Recipe> searchedList;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeIntent = new Intent(this, RecipeShowActivity.class);
        jsonManager = new JsonManager(new File(super.getFilesDir(), json));
        cookingBook = jsonManager.getFromFile().orElse(new CookingBook());
        searchedList = cookingBook.getRecipes();

        //TODO вынести в отдельный метод
        String userUid = getIntent().getStringExtra("user");
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child(userUid).child("cooking_book").setValue(cookingBook, (error, ref) -> Toast.makeText(MainActivity.this, "successful write", Toast.LENGTH_SHORT).show());


        initRecycleView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        cookingBook = jsonManager.getFromFile().orElse(new CookingBook());
        recipeAdapter.setRecipes(cookingBook.getRecipes());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnCloseListener(() -> {
            searchedList = new ArrayList<>(cookingBook.getRecipes());
            recipeAdapter.setRecipes(searchedList);
            return false;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchedList = cookingBook
                        .getRecipes()
                        .stream()
                        .filter(recipe -> recipe.getTitle().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
                recipeAdapter.setRecipes(searchedList);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) recipeAdapter.setRecipes(cookingBook.getRecipes());
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Comparator<Recipe> comparator = null;
        switch (item.getItemId()) {
            case R.id.exit:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                FirebaseAuth.getInstance().signOut();
                startActivity(intent);
                break;
            case R.id.sorting_default:
                searchedList = new ArrayList<>(cookingBook.getRecipes());
                recipeAdapter.setRecipes(searchedList);
                break;
            case R.id.sorting_by_name:
                comparator = (recipe, recipe2) -> recipe.getTitle().compareTo(recipe2.getTitle());
                break;
            case R.id.sorting_by_name_desc:
                comparator = (recipe, recipe2) -> recipe2.getTitle().compareTo(recipe.getTitle());
                break;
        }
        if (comparator != null) {
            searchedList.sort(comparator);
            recipeAdapter.setRecipes(searchedList);
        }
        return true;

    }

    public void createRecipe(View view) {
        Intent recipeCreateIntent = new Intent(this, RecipeCreateActivity.class);
        startActivity(recipeCreateIntent);
    }

    public void initRecycleView() {

        recipeAdapter = new RecipeAdapter(cookingBook.getRecipes(), super.getFilesDir());
        recipeAdapter.setOnClickListener(recipe -> {
            recipeIntent.putExtra("recipeId", recipe.getId());
            startActivity(recipeIntent);
        });
        recipeAdapter.setOnLongClickListener((recipe, view) -> {

            Context context = this;
            PopupMenu popupMenu = new PopupMenu(context, view, Gravity.END);
            popupMenu.inflate(R.menu.recipe_popup_menu);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.edit_item:
                        editItem(recipe.getId());
                        break;
                    case R.id.delete_item:
                        deleteItem(recipe.getId());
                        break;
                }
                return true;
            });
            popupMenu.show();
            return true;
        });

        recipesRecyclerView = findViewById(R.id.recipe_recycler_view);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipesRecyclerView.setAdapter(recipeAdapter);

    }

    public void editItem(Long recipeId) {
        Intent recipeUpdateIntent = new Intent(this, RecipeCreateActivity.class);
        recipeUpdateIntent.putExtra("recipeId", recipeId);
        startActivity(recipeUpdateIntent);
    }

    public void deleteItem(Long recipeId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Delete")
                .setIcon(R.drawable.ic_sharp_warning_18)
                .setMessage("Delete item?")
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    cookingBook = jsonManager.getFromFile().orElse(new CookingBook());
                    cookingBook.removeById(recipeId);
                    jsonManager.writeToFile(cookingBook);
                    searchedList = cookingBook.getRecipes();
                    recipeAdapter.setRecipes(searchedList);
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }
}