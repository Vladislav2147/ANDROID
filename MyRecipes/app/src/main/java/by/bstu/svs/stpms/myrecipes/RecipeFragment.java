package by.bstu.svs.stpms.myrecipes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import by.bstu.svs.stpms.myrecipes.manager.FirebaseManager;
import by.bstu.svs.stpms.myrecipes.model.Recipe;
import by.bstu.svs.stpms.myrecipes.recycler.FireRecipeAdapter;

/**
 * A fragment representing a list of Items.
 */
public class RecipeFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private FireRecipeAdapter mAdapter;
    private String userUid;
    private DatabaseReference db;
    private RecyclerView recipesRecyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RecipeFragment newInstance(int columnCount) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recipesRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recipesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recipesRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            userUid = getActivity().getIntent().getStringExtra("user");
            db = FirebaseDatabase.getInstance().getReference();
            updateAdapterByQuery(db.child(userUid));
        }
        return view;
    }

    //TODO implement listeners
    public void updateAdapterByQuery(Query query) {
        if (mAdapter != null) mAdapter.stopListening();

        FirebaseRecyclerOptions<Recipe> options = new FirebaseRecyclerOptions.Builder<Recipe>()
                .setQuery(query, Recipe.class)
                .build();
        mAdapter = new FireRecipeAdapter(options);
        mAdapter.setOnClickListener(recipe -> {
            Intent recipeIntent = new Intent(getActivity(), RecipeShowActivity.class);
            recipeIntent.putExtra("recipeId", recipe.getId());
            recipeIntent.putExtra("user", userUid);
            startActivity(recipeIntent);
        });
        mAdapter.setOnLongClickListener((recipe, view) -> {

            PopupMenu popupMenu = new PopupMenu(getContext(), view, Gravity.END);
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

        recipesRecyclerView.swapAdapter(mAdapter, true);
        mAdapter.startListening();
//        mAdapter.notifyDataSetChanged();

    }

    public void editItem(String recipeId) {
        Intent recipeUpdateIntent = new Intent(getActivity(), RecipeCreateActivity.class);
        recipeUpdateIntent.putExtra("recipeId", recipeId);
        startActivity(recipeUpdateIntent);
    }

    public void deleteItem(String recipeId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle("Delete")
                .setIcon(R.drawable.ic_sharp_warning_18)
                .setMessage("Delete item?")
                .setPositiveButton("Ok", (dialogInterface, i) -> FirebaseManager.getInstance().delete(recipeId, (error, ref) ->
                        Toast.makeText(
                                getActivity(),
                                "Recipe deleted successfully",
                                Toast.LENGTH_SHORT)
                                .show()
                ))
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mAdapter != null) mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) mAdapter.stopListening();
    }
}