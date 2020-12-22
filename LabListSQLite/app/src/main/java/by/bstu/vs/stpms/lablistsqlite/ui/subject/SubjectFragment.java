package by.bstu.vs.stpms.lablistsqlite.ui.subject;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.function.Consumer;

import by.bstu.vs.stpms.lablistsqlite.R;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Subject;

public class SubjectFragment extends Fragment {

    private SubjectViewModel subjectViewModel;
    private RecyclerView recyclerView;
    private SubjectAdapter subjectAdapter;
    private Consumer<SQLiteException> showError;
    private int termId;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        subjectViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_subject, container, false);
        showError = e -> Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        termId = SubjectFragmentArgs.fromBundle(getArguments()).getTermId();

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

    public void createAddDialog(Subject subjectToEdit) {
        LayoutInflater inflater = getLayoutInflater();
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setMessage("Create subject")
                .setView(inflater.inflate(R.layout.dialog_create_subject, null))
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .show();

        EditText et_name = dialog.findViewById(R.id.et_name);


        if (subjectToEdit != null) {
            et_name.setText(subjectToEdit.getName());
        }

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {

            AwesomeValidation validation = new AwesomeValidation(ValidationStyle.BASIC);

            validation.addValidation(et_name, "^(?=\\s*\\S).*$","Required");

            if(!validation.validate())
                return;
            Subject subject;
            if (subjectToEdit == null) {
                subject = new Subject();
                subject.setTermId(termId);
            } else {
                subject = subjectToEdit;
            }

            subject.setName(et_name.getText().toString());


            if (subjectToEdit == null) {
                subjectViewModel.add(subject, showError);
            } else {
                subjectViewModel.update(subject, showError);
            }
            dialog.dismiss();
        });
    }

    public void deleteSubject(Subject subject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setTitle("Delete")
                .setMessage("Delete item?")
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    subjectViewModel.delete(subject, showError);
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void initRecyclerView(View root) {

        subjectAdapter = new SubjectAdapter();
        subjectViewModel.getSubjectsByTermId(termId).observe(getViewLifecycleOwner(), subjects -> subjectAdapter.setSubjects(subjects));
        subjectAdapter.setOnClickListener(subject -> {
            SubjectFragmentDirections.ActionNavSubjectToNavLab action = SubjectFragmentDirections.actionNavSubjectToNavLab(subject.getId());
            Navigation.findNavController(root).navigate(action);
        });

        subjectAdapter.setOnLongClickListener((subject, view) -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), view, Gravity.END);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.edit_item:
                        createAddDialog(subject);
                        break;
                    case R.id.delete_item:
                        deleteSubject(subject);
                        break;
                }
                return true;
            });
            popupMenu.show();
            return true;
        });

        recyclerView = root.findViewById(R.id.rv_subject);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(subjectAdapter);
    }
}