package by.bstu.vs.stpms.lablist.ui.subject;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.vs.stpms.lablist.model.entity.Subject;
import by.bstu.vs.stpms.lablist.model.repository.SubjectRepository;
import by.bstu.vs.stpms.lablist.ui.AbstractCrudViewModel;

public class SubjectViewModel extends AbstractCrudViewModel<Subject, SubjectRepository> {

    public SubjectViewModel(Application application) {
        super(application);
        repository = new SubjectRepository(application);
        listLiveData = repository.getSubjectsByTermId(0);
    }

    public LiveData<List<Subject>> getSubjectsByTermId(int id) {
        listLiveData = repository.getSubjectsByTermId(id);
        return listLiveData;
    }
}