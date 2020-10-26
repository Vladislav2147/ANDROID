package by.bstu.svs.stpms.myrecipes.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import by.bstu.svs.stpms.myrecipes.R;
import by.bstu.svs.stpms.myrecipes.model.Recipe;

public class FireRecipeAdapter extends FirebaseRecyclerAdapter<Recipe, RecipeViewHolder> {

    public interface onClickListener{
        void onVariantClick(Recipe recipe);
    }

    public interface onLongClickListener{
        boolean onLongVariantClick(Recipe recipe, View view);
    }

    private RecipeAdapter.onClickListener onClickListener;
    private RecipeAdapter.onLongClickListener onLongClickListener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FireRecipeAdapter(FirebaseRecyclerOptions<Recipe> options) {
        super(options);
    }

    public void setOnClickListener(RecipeAdapter.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(RecipeAdapter.onLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_view, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(RecipeViewHolder recipeViewHolder, int i, Recipe recipe) {
        recipeViewHolder.bind(recipe);
        if (onClickListener != null) {
            recipeViewHolder.itemView.setOnClickListener(view -> onClickListener.onVariantClick(recipe));
        }
        if (onLongClickListener != null) {
            recipeViewHolder.itemView.setOnLongClickListener(view -> onLongClickListener.onLongVariantClick(recipe, view));
        }
    }
}
