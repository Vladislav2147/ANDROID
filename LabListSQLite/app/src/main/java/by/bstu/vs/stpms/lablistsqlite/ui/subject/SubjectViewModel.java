package by.bstu.vs.stpms.lablistsqlite.ui.subject;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Subject;
import by.bstu.vs.stpms.lablistsqlite.model.repository.impl.SubjectRepository;
import by.bstu.vs.stpms.lablistsqlite.ui.AbstractCrudViewModel;

public class SubjectViewModel extends AbstractCrudViewModel<Subject, SubjectRepository> {

    @Inject
    public SubjectViewModel(SubjectRepository repository) {
        super(repository);
        listLiveData = repository.getSubjectsByTermId(0);
    }

    public LiveData<List<Subject>> getSubjectsByTermId(int id) {
        listLiveData = repository.getSubjectsByTermId(id);
        return listLiveData;
    }
}