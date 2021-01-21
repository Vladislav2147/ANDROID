package by.bstu.vs.stpms.lablistsqlite.ui.lab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;
import by.bstu.vs.stpms.lablistsqlite.model.repository.impl.LabRepository;
import by.bstu.vs.stpms.lablistsqlite.ui.AbstractCrudViewModel;
import io.reactivex.Completable;
import lombok.Getter;

public class LabViewModel extends AbstractCrudViewModel<Lab, LabRepository> {

    private int subjectId;
    private boolean createMode = true;

    @Getter
    private LiveData<Lab> labLiveData;
    public LabViewModel(@NonNull Application application) {
        super(application);
        repository = new LabRepository(application);
        listLiveData = repository.getLabsBySubjectId(0);
    }

    public LiveData<List<Lab>> getLabsBySubjectId(int id) {
        listLiveData = repository.getLabsBySubjectId(id);
        return listLiveData;
    }

    public LiveData<List<Lab>> getLabsBySubjectIdSortedByState(int id) {
        listLiveData = repository.getLabsBySubjectIdSortedByState(id);
        return listLiveData;
    }

    public LiveData<Lab> getLabById(int id) {
        labLiveData = repository.getById(id);
        return labLiveData;
    }

    public void setLabLiveData(Lab lab) {
        if (lab == null) {
            createMode = true;
            lab = new Lab();
        } else {
            createMode = false;
        }
        this.labLiveData = new MutableLiveData<>(lab);
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public Completable save() {
        labLiveData.getValue().setSubjectId(subjectId);
        Lab lab = labLiveData.getValue();
        return createMode ? add(lab) : update(lab);
    }

}