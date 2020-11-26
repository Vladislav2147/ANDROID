package by.bstu.vs.stpms.lablist.ui.subject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import by.bstu.vs.stpms.lablist.R;
import by.bstu.vs.stpms.lablist.model.entity.Term;

public class SubjectFragment extends Fragment {

    private SubjectViewModel subjectViewModel;
    private RecyclerView recyclerView;
    private SubjectAdapter subjectAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        subjectViewModel = new ViewModelProvider(this).get(SubjectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_subject, container, false);

        int termId = SubjectFragmentArgs.fromBundle(getArguments()).getTermId();

        subjectAdapter = new SubjectAdapter();
        subjectViewModel.getSubjectsByTermId(termId).observe(getViewLifecycleOwner(), subjects -> subjectAdapter.setSubjects(subjects));
        subjectAdapter.setOnClickListener(subject -> {
            SubjectFragmentDirections.ActionNavSubjectToNavLab action = SubjectFragmentDirections.actionNavSubjectToNavLab(subject.getId());
            Navigation.findNavController(root).navigate(action);
        });

        recyclerView = root.findViewById(R.id.rv_subject);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(subjectAdapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(this::createAddDialog);
    }

    public void createAddDialog(View view) {
        LayoutInflater inflater = getLayoutInflater();
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setMessage("Create subject")
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
//
//            try {
//                termViewModel.addTerm(term, e -> Toast.makeText(TermFragment.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
//                dialog.dismiss();
//            } catch (SQLiteException e) {
//                Toast.makeText(TermFragment.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
        });
    }
}