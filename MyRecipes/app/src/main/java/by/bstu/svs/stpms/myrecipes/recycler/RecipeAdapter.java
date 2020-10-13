package by.bstu.svs.stpms.myrecipes.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import by.bstu.svs.stpms.myrecipes.R;
import by.bstu.svs.stpms.myrecipes.manager.ImageManager;
import by.bstu.svs.stpms.myrecipes.model.CookingBook;
import by.bstu.svs.stpms.myrecipes.model.Recipe;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ListenerViewHolder> {

    private CookingBook book;
    private File filesDir;

    public RecipeAdapter(CookingBook book, File filesDir) {
        this.book = book;
        this.filesDir = filesDir;
    }


    public void setBook(CookingBook book) {
        this.book = book;
        notifyDataSetChanged();
    }

    public interface onClickListener{
        void onVariantClick(Recipe recipe);
    }
    public interface onLongClickListener{
        boolean onLongVariantClick(Recipe recipe, View view);
    }

    private onClickListener onClickListener;
    private onLongClickListener onLongClickListener;

    @Override
    public void onBindViewHolder(@NonNull ListenerViewHolder holder, int position) {
        final Recipe recipe = book.getRecipes().get(position);
        holder.bind(recipe);
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(view -> onClickListener.onVariantClick(recipe));
        }
        if (onLongClickListener != null) {
            holder.itemView.setOnLongClickListener(view -> onLongClickListener.onLongVariantClick(recipe, view));
        }
    }

    public void setOnClickListener(onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(onLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }


    @NonNull
    @Override
    public ListenerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_view, parent, false);
        return new ListenerViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return book == null ? 0 : book.getRecipes().size();
    }

    class ListenerViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_picture;
        private TextView tv_title;
        private TextView tv_steps;

        public ListenerViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_picture = itemView.findViewById(R.id.picture);
            tv_title = itemView.findViewById(R.id.title);
            tv_steps = itemView.findViewById(R.id.steps);
        }

        public void bind(Recipe recipe) {

            String title = recipe.getTitle();
            tv_title.setText(title);
            tv_steps.setText(recipe.getSteps());

            if (recipe.getPicture() == null) {
                iv_picture.setImageResource(R.drawable.ic_no_image);
            }
            else {
                ImageManager.getBitMapFromFile(new File(filesDir.getAbsolutePath() + "/images", recipe.getPicture()))
                        .ifPresent(iv_picture::setImageBitmap);
            }

        }

    }

}