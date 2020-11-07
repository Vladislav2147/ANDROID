package by.bstu.svs.stpms.myrecipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.File;

import by.bstu.svs.stpms.myrecipes.manager.DatabaseRecipeManager;
import by.bstu.svs.stpms.myrecipes.manager.ImageManager;
import by.bstu.svs.stpms.myrecipes.manager.exception.SQLiteDatabaseException;
import by.bstu.svs.stpms.myrecipes.model.Recipe;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailsFragment extends Fragment {

    private TextView titleTextView;
    private TextView categoryTextView;
    private TextView timeToCookTextView;
    private TextView ingredientsTextView;
    private TextView stepsTextView;
    private ImageView imageView;

    private static final String ARG_RECIPE_ID = "recipeId";


    private Integer recipeId;
    private DatabaseRecipeManager databaseRecipeManager;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipeId selected recipe identifier.
     * @return A new instance of fragment RecipeDetailsFragment.
     */
    public static RecipeDetailsFragment newInstance(Integer recipeId) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE_ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeId = (Integer) getArguments().getSerializable(ARG_RECIPE_ID);
        }
        databaseRecipeManager = DatabaseRecipeManager.getInstance(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        titleTextView = view.findViewById(R.id.title);
        categoryTextView = view.findViewById(R.id.category);
        timeToCookTextView = view.findViewById(R.id.time_to_cook);
        ingredientsTextView = view.findViewById(R.id.ingredients);
        stepsTextView = view.findViewById(R.id.steps);
        imageView = view.findViewById(R.id.image);

        try {
            showRecipe(databaseRecipeManager.getById(recipeId));
        } catch (SQLiteDatabaseException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void showRecipe(Recipe recipe) {

        titleTextView.setText(recipe.getTitle());
        categoryTextView.setText(recipe.getCategory().getName());
        timeToCookTextView.setText(recipe.getTimeToCook().toString());
        ingredientsTextView.setText(recipe.getIngredients());
        stepsTextView.setText(recipe.getSteps());

        if (recipe.getPicture() != null) {
            File image = new File(getActivity().getFilesDir().getAbsolutePath() + "/images", recipe.getPicture());
            ImageManager.getBitMapFromFile(image).ifPresent(imageView::setImageBitmap);
        }

    }
}