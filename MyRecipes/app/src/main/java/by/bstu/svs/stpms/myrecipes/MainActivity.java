package by.bstu.svs.stpms.myrecipes;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MainActivity extends AppCompatActivity {

    private FrameLayout listContainer;
    private FrameLayout detailsContainer;


    private RecipeFragment recipeListFragment;

    private String userUid;
    private DatabaseReference db;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listContainer = findViewById(R.id.recipe_container);
        detailsContainer = findViewById(R.id.recipe_details_container);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            detailsContainer.setVisibility(View.VISIBLE);

        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // In portrait
            detailsContainer.setVisibility(View.GONE);
        }

        fragmentManager =  getSupportFragmentManager();
        recipeListFragment = new RecipeFragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.recipe_container, recipeListFragment)
                .commit();
        userUid = getIntent().getStringExtra("user");
        db = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnCloseListener(() -> {
            recipeListFragment.updateAdapterByQuery(db.child(userUid));
            return false;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                Query fireQuery = db.child(userUid).orderByChild("title").startAt(queryText)
                        .endAt(queryText+"\uf8ff");
                recipeListFragment.updateAdapterByQuery(fireQuery);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() == 0) {
                    recipeListFragment.updateAdapterByQuery(db.child(userUid));
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
            recipeListFragment.updateAdapterByQuery(query);
        }
        return true;

    }

    public void createRecipe(View view) {
        Intent recipeCreateIntent = new Intent(this, RecipeCreateActivity.class);
        startActivity(recipeCreateIntent);
    }

//    @Override
//    public void onBackPressed() {
//        finishAffinity();
//        super.onBackPressed();
//    }
}