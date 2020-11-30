package by.bstu.vs.stpms.lablist.ui.lab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.vs.stpms.lablist.model.entity.Lab;
import by.bstu.vs.stpms.lablist.model.repository.LabRepository;
import by.bstu.vs.stpms.lablist.ui.AbstractCrudViewModel;
import lombok.Getter;

public class LabViewModel extends AbstractCrudViewModel<Lab, LabRepository> {
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

    public LiveData<Lab> getLabById(int id) {
        labLiveData = repository.getById(id);
        return labLiveData;
    }
}