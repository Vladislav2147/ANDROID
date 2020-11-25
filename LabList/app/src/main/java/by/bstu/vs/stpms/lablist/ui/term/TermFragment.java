package by.bstu.vs.stpms.lablist.ui.term;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import by.bstu.vs.stpms.lablist.R;
import by.bstu.vs.stpms.lablist.model.entity.Term;

public class TermFragment extends Fragment {

    private TermViewModel termViewModel;
    private RecyclerView recyclerView;
    private TermAdapter termAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        View root = inflater.inflate(R.layout.fragment_term, container, false);

        termAdapter = new TermAdapter();
        termViewModel.getTerms().observe(getViewLifecycleOwner(), terms -> {
            termAdapter.setTerms(terms);
        });

        recyclerView = root.findViewById(R.id.rv_term);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(termAdapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab = getView().findViewById(R.id.fab);
        fab.setOnClickListener(this::createAddDialog);
    }

    public void createAddDialog(View view) {
        LayoutInflater inflater = getLayoutInflater();
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setMessage("Create term")
                .setView(inflater.inflate(R.layout.dialog_create_term, null))
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(view1 -> {

            AwesomeValidation validation = new AwesomeValidation(ValidationStyle.BASIC);
            EditText et_course = dialog.findViewById(R.id.et_course);
            EditText et_semester = dialog.findViewById(R.id.et_semester);

            validation.addValidation(et_course, "^[1-5]$","Invalid");
            validation.addValidation(et_semester, "^[1-2]$","Invalid");

            if(!validation.validate())
                return;

            Term term = new Term();
            term.setCourse(Integer.valueOf(et_course.getText().toString()));
            term.setSemester(Integer.valueOf(et_semester.getText().toString()));

            try {
                termViewModel.addTerm(term);
                dialog.dismiss();
            } catch (SQLiteException e) {
                Toast.makeText(TermFragment.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}