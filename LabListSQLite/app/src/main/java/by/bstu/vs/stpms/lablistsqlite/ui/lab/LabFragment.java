package by.bstu.vs.stpms.lablistsqlite.ui.lab;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import by.bstu.vs.stpms.lablistsqlite.R;
import by.bstu.vs.stpms.lablistsqlite.application.LabListApplication;
import by.bstu.vs.stpms.lablistsqlite.logging.FileLog;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;
import by.bstu.vs.stpms.lablistsqlite.model.observable.SimpleCompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LabFragment extends Fragment {

    private final static String TAG = "LabCreateFragment";
    @Inject
    LabViewModel labViewModel;
    private RecyclerView recyclerView;
    private LabAdapter labAdapter;
    private int subjectId;
    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        ((LabListApplication) context.getApplicationContext()).appComponent.inject(this);
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_lab, container, false);
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
                    labViewModel.delete(lab)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SimpleCompletableObserver(
                                    () -> {
                                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                        FileLog.getInstance().info(TAG, "deleteLab: success " + lab);
                                    },
                                    (e) -> {
                                        FileLog.getInstance().error(TAG, "deleteLab: " + lab + " delete failed", e);
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
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
        labAdapter.setOnIsPassedPropertyChanged(lab -> {
            labViewModel.update(lab)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SimpleCompletableObserver(
                            () -> FileLog.getInstance().info(TAG, "update checkbox: success " + lab),
                            (e) -> {
                                FileLog.getInstance().error(TAG, "update checkbox: " + lab + " failed", e);
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        });
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.lab_sorting_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_default:
                labViewModel.getLabsBySubjectId(subjectId);
                return true;
            case R.id.sort_not_done_first:
                labViewModel.getLabsBySubjectIdSortedByState(subjectId);
                return true;
            default:
                break;
        }

        return false;
    }
}