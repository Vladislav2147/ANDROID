package by.bstu.svs.stpms.myrecipes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import by.bstu.svs.stpms.myrecipes.manager.FirebaseManager;
import by.bstu.svs.stpms.myrecipes.manager.JsonManager;
import by.bstu.svs.stpms.myrecipes.model.Recipe;
import by.bstu.svs.stpms.myrecipes.recycler.FireRecipeAdapter;
import by.bstu.svs.stpms.myrecipes.recycler.RecipeAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String json = "cooking_book.json";
    private RecyclerView recipesRecyclerView;
    private RecipeAdapter recipeAdapter;
    private JsonManager jsonManager;
    private List<Recipe> recipes;
    private Intent recipeIntent;

    FireRecipeAdapter mAdapter;
    private String userUid;

    private List<Recipe> searchedList;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeIntent = new Intent(this, RecipeShowActivity.class);
        FirebaseManager.getInstance();

        userUid = getIntent().getStringExtra("user");
        myRef = FirebaseDatabase.getInstance().getReference();
        Query query = myRef.child(userUid);
        FirebaseRecyclerOptions<Recipe> options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(query, Recipe.class)
                .build();
        mAdapter = new FireRecipeAdapter(options);
        mAdapter.setOnClickListener(recipe -> {
            recipeIntent.putExtra("recipeId", recipe.getId());
            recipeIntent.putExtra("user", userUid);
            startActivity(recipeIntent);
        });
        mAdapter.setOnLongClickListener((recipe, view) -> {

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
        recipesRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnCloseListener(() -> {
            searchedList = new ArrayList<>(recipes);
            recipeAdapter.setRecipes(searchedList);
            return false;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchedList = recipes
                        .stream()
                        .filter(recipe -> recipe.getTitle().toLowerCase().contains(query.toLowerCase()))
                        .collect(Collectors.toList());
                recipeAdapter.setRecipes(searchedList);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) recipeAdapter.setRecipes(recipes);
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
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                FirebaseAuth.getInstance().signOut();
                startActivity(intent);
                break;
            case R.id.sorting_default:
                searchedList = new ArrayList<>(recipes);
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

//    public void initRecycleView() {
//
//        recipeAdapter = new RecipeAdapter(cookingBook.getRecipes(), super.getFilesDir());
//        recipeAdapter.setOnClickListener(recipe -> {
//            recipeIntent.putExtra("recipeId", recipe.getId());
//            startActivity(recipeIntent);
//        });
//        recipeAdapter.setOnLongClickListener((recipe, view) -> {
//
//            Context context = this;
//            PopupMenu popupMenu = new PopupMenu(context, view, Gravity.END);
//            popupMenu.inflate(R.menu.recipe_popup_menu);
//            popupMenu.setOnMenuItemClickListener(menuItem -> {
//                switch (menuItem.getItemId()) {
//                    case R.id.edit_item:
//                        editItem(recipe.getId());
//                        break;
//                    case R.id.delete_item:
//                        deleteItem(recipe.getId());
//                        break;
//                }
//                return true;
//            });
//            popupMenu.show();
//            return true;
//        });
//
//        recipesRecyclerView = findViewById(R.id.recipe_recycler_view);
//        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recipesRecyclerView.setAdapter(mAdapter);
//
//    }

    public void editItem(String recipeId) {
        Intent recipeUpdateIntent = new Intent(this, RecipeCreateActivity.class);
        recipeUpdateIntent.putExtra("recipeId", recipeId);
        startActivity(recipeUpdateIntent);
    }

    public void deleteItem(String recipeId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Delete")
                .setIcon(R.drawable.ic_sharp_warning_18)
                .setMessage("Delete item?")
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    FirebaseDatabase.getInstance().getReference().child(userUid).child("recipes").orderByChild("id").equalTo(recipeId);
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }
}