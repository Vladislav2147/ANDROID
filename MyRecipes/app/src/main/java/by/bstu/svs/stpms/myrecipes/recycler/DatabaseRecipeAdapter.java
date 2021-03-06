package by.bstu.svs.stpms.myrecipes.recycler;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import by.bstu.svs.stpms.myrecipes.manager.DatabaseRecipeManager;
import by.bstu.svs.stpms.myrecipes.model.Recipe;

public class DatabaseRecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    public interface OnClickListener {
        void onVariantClick(Recipe recipe);
    }

    public interface OnLongClickListener {
        boolean onLongVariantClick(Recipe recipe, View view);
    }

    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;

    private Cursor cursor;
    private Context context;
    private int recipeLayoutId;


    public DatabaseRecipeAdapter(Cursor cursor, int recipeLayoutId) {
        this.cursor = cursor;
        this.recipeLayoutId = recipeLayoutId;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(recipeLayoutId, parent, false);
        File file = parent.getContext().getFilesDir();
        return new RecipeViewHolder(view, file);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        cursor.moveToFirst();
        cursor.move(position);
        Recipe recipe = DatabaseRecipeManager.getInstance(context).getRecipeByCursor(cursor);

        holder.bind(recipe);
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(view -> onClickListener.onVariantClick(recipe));
        }
        if (onLongClickListener != null) {
            holder.itemView.setOnLongClickListener(view -> onLongClickListener.onLongVariantClick(recipe, view));
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
