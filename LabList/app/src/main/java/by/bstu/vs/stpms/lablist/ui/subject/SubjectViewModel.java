package by.bstu.vs.stpms.lablist.ui.subject;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.vs.stpms.lablist.model.entity.Subject;
import by.bstu.vs.stpms.lablist.model.repository.SubjectRepository;

public class SubjectViewModel extends AndroidViewModel {

    private LiveData<List<Subject>> subjects;
    private SubjectRepository repository;

    public SubjectViewModel(Application application) {
        super(application);
        repository = new SubjectRepository(application);
        subjects = repository.getSubjectsByTermId(0);
    }

    public LiveData<List<Subject>> getSubjectsByTermId(int id) {
        subjects = repository.getSubjectsByTermId(id);
        return subjects;
    }
}