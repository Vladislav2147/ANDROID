package by.bstu.vs.stpms.lablist.ui.lab;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.function.Consumer;

import by.bstu.vs.stpms.lablist.R;
import by.bstu.vs.stpms.lablist.model.entity.Lab;

public class LabFragment extends Fragment {

    private LabViewModel labViewModel;
    private RecyclerView recyclerView;
    private LabAdapter labAdapter;
    private Consumer<SQLiteException> showError;
    private int subjectId;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        labViewModel = new ViewModelProvider(this).get(LabViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lab, container, false);
        showError = e -> Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        subjectId = LabFragmentArgs.fromBundle(getArguments()).getSubjectId();
        NavHostFragment navHostFragment = (NavHostFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        initRecyclerView(root);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            LabFragmentDirections.ActionNavLabToLabCreateFragment action = LabFragmentDirections.actionNavLabToLabCreateFragment(subjectId);
            navController.navigate(action);
        });
    }

    public void deleteLab(Lab lab) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setTitle("Delete")
                .setMessage("Delete item?")
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    labViewModel.delete(lab, showError);
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void initRecyclerView(View root) {
        labAdapter = new LabAdapter();
        labViewModel.getLabsBySubjectId(subjectId).observe(getViewLifecycleOwner(), labs -> labAdapter.setLabs(labs));
        labAdapter.setOnClickListener(lab -> {
            LabFragmentDirections.ActionNavLabToLabDetailsFragment action = LabFragmentDirections.actionNavLabToLabDetailsFragment(lab.getId());
            navController.navigate(action);
        });
        labAdapter.setOnIsPassedPropertyChanged(lab -> labViewModel.update(lab, showError));
        labAdapter.setOnLongClickListener((lab, view) -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), view, Gravity.END);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.edit_item:
                        LabFragmentDirections.ActionNavLabToLabCreateFragment action = LabFragmentDirections.actionNavLabToLabCreateFragment(subjectId);
                        action.setLab(lab);
                        navController.navigate(action);
                        break;
                    case R.id.delete_item:
                        deleteLab(lab);
                        break;
                }
                return true;
            });
            popupMenu.show();
            return true;
        });

        recyclerView = root.findViewById(R.id.rv_lab);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(labAdapter);
    }
}