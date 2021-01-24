package by.bstu.vs.stpms.lablistsqlite.ui.subject;

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
import by.bstu.vs.stpms.lablistsqlite.model.entity.Subject;
import by.bstu.vs.stpms.lablistsqlite.model.observable.SimpleCompletableObserver;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SubjectFragment extends Fragment {

    private final static String TAG = "SubjectFragment";
    @Inject
    SubjectViewModel subjectViewModel;
    private RecyclerView recyclerView;
    private SubjectAdapter subjectAdapter;
    private int termId;

    @Override
    public void onAttach(@NonNull Context context) {
        ((LabListApplication) context.getApplicationContext()).appComponent.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_subject, container, false);
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

            Completable completable = subjectToEdit == null ? subjectViewModel.add(subject) : subjectViewModel.update(subject);
            completable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SimpleCompletableObserver(
                            () -> {
                                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                                FileLog.getInstance().info(TAG, "createAddDialog: save success " + subject);
                                dialog.dismiss();
                            },
                            (e) -> {
                                FileLog.getInstance().error(TAG, "createAddDialog: " + subject + " save failed", e);
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        });
    }

    public void deleteSubject(Subject subject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setTitle("Delete")
                .setMessage("Delete item?")
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    subjectViewModel.delete(subject)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SimpleCompletableObserver(
                                    () -> {
                                        Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                        FileLog.getInstance().info(TAG, "deleteSubject: delete success " + subject);
                                    },
                                    (e) -> {
                                        FileLog.getInstance().error(TAG, "deleteSubject: " + subject + " delete failed", e);
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
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