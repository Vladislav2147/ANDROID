package by.bstu.svs.stpms.myrecipes;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import by.bstu.svs.stpms.myrecipes.manager.DatabaseRecipeManager;
import by.bstu.svs.stpms.myrecipes.model.Query;
import by.bstu.svs.stpms.myrecipes.recycler.DatabaseRecipeAdapter;

/**
 * A fragment representing a list of Items.
 */
public class RecipeFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;

    private DatabaseRecipeAdapter mAdapter;

    private RecyclerView recipesRecyclerView;

    private DatabaseRecipeAdapter.OnClickListener onClickListener;
    private DatabaseRecipeAdapter.OnLongClickListener onLongClickListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeFragment() {
    }
    
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

            updateAdapterByCursor(DatabaseRecipeManager.getInstance(getActivity()).getCursorByQuery(new Query()));
        }
        return view;
    }

    public void updateAdapterByCursor(Cursor cursor) {

        mAdapter = new DatabaseRecipeAdapter(cursor);
        mAdapter.setContext(getActivity());
        mAdapter.setOnClickListener(onClickListener);
        mAdapter.setOnLongClickListener(onLongClickListener);

        recipesRecyclerView.swapAdapter(mAdapter, true);

    }

    public RecyclerView getRecipesRecyclerView() {
        return recipesRecyclerView;
    }

    public void setOnClickListener(DatabaseRecipeAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(DatabaseRecipeAdapter.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }



}