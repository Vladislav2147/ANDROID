package by.bstu.svs.stpms.myrecipes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

import java.util.List;

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

    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeIntent = new Intent(this, RecipeShowActivity.class);

        userUid = getIntent().getStringExtra("user");
        db = FirebaseDatabase.getInstance().getReference();

        recipesRecyclerView = findViewById(R.id.recipe_recycler_view);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query = db.child(userUid);
        updateAdapterByQuery(query);

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
            updateAdapterByQuery(db.child(userUid));
            return false;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                Query fireQuery = db.child(userUid).orderByChild("title").startAt(queryText)
                        .endAt(queryText+"\uf8ff");
                updateAdapterByQuery(fireQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {

                    updateAdapterByQuery(db.child(userUid));
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
            case R.id.exit:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                FirebaseAuth.getInstance().signOut();
                startActivity(intent);
                break;
            case R.id.sorting_default:
                query = db.child(userUid);
                break;
            case R.id.sorting_by_name:
                query = db.child(userUid).orderByChild("title");
                break;
            case R.id.sorting_by_category:
                query = db.child(userUid).orderByChild("category");
                break;
        }
        if (query != null) {
            updateAdapterByQuery(query);

            recipesRecyclerView.setAdapter(mAdapter);
            mAdapter.startListening();
        }
        return true;

    }

    public void createRecipe(View view) {
        Intent recipeCreateIntent = new Intent(this, RecipeCreateActivity.class);
        startActivity(recipeCreateIntent);
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
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    FirebaseManager.getInstance().delete(recipeId, (error, ref) ->
                            Toast.makeText(
                                    MainActivity.this,
                                    "Recipe deleted successfully",
                                    Toast.LENGTH_SHORT)
                                    .show()
                    );
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    private void updateAdapterByQuery(Query query) {
        if (mAdapter != null) mAdapter.stopListening();

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

        recipesRecyclerView.setAdapter(mAdapter);
        mAdapter.startListening();
    }
}