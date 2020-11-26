package by.bstu.vs.stpms.lablist.ui.term;

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

import by.bstu.vs.stpms.lablist.R;
import by.bstu.vs.stpms.lablist.model.entity.Term;

public class TermFragment extends Fragment {

    private TermViewModel termViewModel;
    private RecyclerView recyclerView;
    private TermAdapter termAdapter;
    private Consumer<SQLiteException> showError;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        showError = e -> Toast.makeText(TermFragment.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
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

            if (termToEdit == null) {
                termViewModel.addTerm(term, showError);
            } else {
                termViewModel.updateTerm(term, showError);
            }
            dialog.dismiss();
        });
    }

    public void deleteTerm(Term term) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setTitle("Delete")
                .setMessage("Delete item?")
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    termViewModel.deleteTerm(term, showError);
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void initRecyclerView(View root) {
        termAdapter = new TermAdapter();
        termViewModel.getTerms().observe(getViewLifecycleOwner(), terms -> termAdapter.setTerms(terms));
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