package by.bstu.vs.stpms.lablistsqlite.ui.term;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import by.bstu.vs.stpms.lablistsqlite.R;
import by.bstu.vs.stpms.lablistsqlite.application.LabListApplication;
import by.bstu.vs.stpms.lablistsqlite.logging.FileLog;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Term;
import by.bstu.vs.stpms.lablistsqlite.model.observable.SimpleCompletableObserver;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TermFragment extends Fragment {

    private final static String TAG = "TermFragment";
    @Inject
    TermViewModel termViewModel;
    private RecyclerView recyclerView;
    private TermAdapter termAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        ((LabListApplication) context.getApplicationContext()).appComponent.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_term, container, false);
        initRecyclerView(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            createAddDialog(null);
        });
    }

    public void createAddDialog(Term termToEdit) {
        LayoutInflater inflater = getLayoutInflater();
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setMessage("Create term")
                .setView(inflater.inflate(R.layout.dialog_create_term, null))
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .show();

        EditText et_course = dialog.findViewById(R.id.et_course);
        EditText et_semester = dialog.findViewById(R.id.et_semester);

        if (termToEdit != null) {
            et_course.setText(String.valueOf(termToEdit.getCourse()));
            et_semester.setText(String.valueOf(termToEdit.getSemester()));
        }

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {

            AwesomeValidation validation = new AwesomeValidation(ValidationStyle.BASIC);

            validation.addValidation(et_course, "^[1-5]$","Invalid");
            validation.addValidation(et_semester, "^[1-2]$","Invalid");

            if(!validation.validate())
                return;
            Term term;
            if (termToEdit == null) {
                term = new Term();
            } else {
                term = termToEdit;
            }

            term.setCourse(Integer.valueOf(et_course.getText().toString()));
            term.setSemester(Integer.valueOf(et_semester.getText().toString()));

            Completable completable = termToEdit == null ? termViewModel.add(term) : termViewModel.update(term);
            completable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SimpleCompletableObserver(
                            () -> {
                                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                                FileLog.getInstance().info(TAG, "createAddDialog: save success " + term);
                                dialog.dismiss();
                            },
                            (e) -> {
                                FileLog.getInstance().error(TAG, "createAddDialog: " + term + " save failed", e);
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));

        });
    }

    public void deleteTerm(Term term) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setTitle("Delete")
                .setMessage("Delete item?")
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    termViewModel.delete(term)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SimpleCompletableObserver(
                                    () -> {
                                        Toast.makeText(TermFragment.this.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                        FileLog.getInstance().info(TAG, "deleteSubject: delete success " + term);
                                    },
                                    (e) -> {
                                        FileLog.getInstance().error(TAG, "deleteSubject: " + term + " delete failed", e);
                                        Toast.makeText(TermFragment.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void initRecyclerView(View root) {
        termAdapter = new TermAdapter();
        termViewModel.getItems().observe(getViewLifecycleOwner(), terms -> termAdapter.setTerms(terms));
        termAdapter.setOnClickListener(term -> {
            TermFragmentDirections.ActionNavTermToNavSubject action = TermFragmentDirections.actionNavTermToNavSubject(term.getId());
            Navigation.findNavController(root).navigate(action);
        });
        termAdapter.setOnLongClickListener((term, view) -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), view, Gravity.END);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.edit_item:
                        createAddDialog(term);
                        break;
                    case R.id.delete_item:
                        deleteTerm(term);
                        break;
                }
                return true;
            });
            popupMenu.show();
            return true;
        });

        recyclerView = root.findViewById(R.id.rv_term);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(termAdapter);
    }
}