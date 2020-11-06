package by.bstu.svs.stpms.myrecipes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.Query;

import by.bstu.svs.stpms.myrecipes.manager.DatabaseOpenHelper;
import by.bstu.svs.stpms.myrecipes.manager.DatabaseRecipeManager;

public class MainActivity extends AppCompatActivity {

    private FrameLayout listContainer;
    private FrameLayout detailsContainer;


    private RecipeFragment recipeListFragment;
    private RecipeDetailsFragment recipeDetailsFragment;

    private SQLiteDatabase database;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listContainer = findViewById(R.id.recipe_container);
        detailsContainer = findViewById(R.id.recipe_details_container);

        fragmentManager =  getSupportFragmentManager();
        recipeListFragment = new RecipeFragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.recipe_container, recipeListFragment)
                .commit();

        DatabaseOpenHelper databaseOpenHelper = new DatabaseOpenHelper(this);
        database = databaseOpenHelper.getReadableDatabase();
        DatabaseRecipeManager manager = DatabaseRecipeManager.getInstance();
        manager.setDatabase(database);

        int orientation = getResources().getConfiguration().orientation;
        initShowingDetails(orientation);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnCloseListener(() -> {
            recipeListFragment.updateAdapterByQuery(database.child(userUid));
            return false;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                Query fireQuery = database.child(userUid).orderByChild("title").startAt(queryText)
                        .endAt(queryText+"\uf8ff");
                recipeListFragment.updateAdapterByQuery(fireQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    recipeListFragment.updateAdapterByQuery(database.child(userUid));
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Query query = null;
        switch (item.getItemId()) {
            case R.id.scroll_up:
                recipeListFragment.getRecipesRecyclerView().smoothScrollToPosition(0);
                break;
            case R.id.sorting_default:
                query = database.child(userUid);
                break;
            case R.id.sorting_by_name:
                query = database.child(userUid).orderByChild("title");
                break;
            case R.id.sorting_by_category:
                query = database.child(userUid).orderByChild("category");
                break;
        }
        if (query != null) {
            recipeListFragment.updateAdapterByQuery(query);
        }
        return true;

    }

    public void createRecipe(View view) {
        Intent recipeCreateIntent = new Intent(this, RecipeCreateActivity.class);
        startActivity(recipeCreateIntent);
    }

    private void initShowingDetails(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            detailsContainer.setVisibility(View.VISIBLE);

            recipeListFragment.setOnClickListener(recipe -> {
                recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipe.getId());

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.recipe_details_container, recipeDetailsFragment)
                        .addToBackStack(null)
                        .commit();
            });

        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // In portrait
            detailsContainer.setVisibility(View.GONE);
            recipeListFragment.setOnClickListener(recipe -> {
                Intent recipeIntent = new Intent(this, RecipeShowActivity.class);
                recipeIntent.putExtra("recipeId", recipe.getId());
                startActivity(recipeIntent);
            });
        }

        recipeListFragment.setOnLongClickListener((recipe, view) -> {

            PopupMenu popupMenu = new PopupMenu(this, view, Gravity.END);
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
    }

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
                .setPositiveButton("Ok", (dialogInterface, i) -> DatabaseRecipeManager.getInstance().delete(recipeId, (error, ref) ->
                        Toast.makeText(
                                MainActivity.this,
                                "Recipe deleted successfully",
                                Toast.LENGTH_SHORT)
                                .show()
                ))
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }
}