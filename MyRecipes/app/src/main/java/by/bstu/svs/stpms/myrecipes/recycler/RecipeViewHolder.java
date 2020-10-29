package by.bstu.svs.stpms.myrecipes.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import by.bstu.svs.stpms.myrecipes.R;
import by.bstu.svs.stpms.myrecipes.manager.ImageManager;
import by.bstu.svs.stpms.myrecipes.model.Recipe;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    private final ImageView iv_picture;
    private final TextView tv_title;
    private final TextView tv_category;
    private final File filesDir;

    public RecipeViewHolder(@NonNull View itemView, File filesDir) {
        super(itemView);
        iv_picture = itemView.findViewById(R.id.picture);
        tv_title = itemView.findViewById(R.id.title);
        tv_category = itemView.findViewById(R.id.category);
        this.filesDir = filesDir;
    }

    public void bind(Recipe recipe) {

        String title = recipe.getTitle();
        tv_title.setText(title);
        tv_category.setText(recipe.getCategory().getName());

        iv_picture.setImageResource(R.drawable.ic_no_image);

        if (recipe.getPicture() == null) {
            iv_picture.setImageResource(R.drawable.ic_no_image);
        } else {
            ImageManager.getBitMapFromFile(new File(filesDir.getAbsolutePath() + "/images", recipe.getPicture()))
                    .ifPresent(iv_picture::setImageBitmap);
        }

    }
}