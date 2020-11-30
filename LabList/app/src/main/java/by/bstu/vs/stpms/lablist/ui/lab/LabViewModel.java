package by.bstu.vs.stpms.lablist.ui.lab;

import android.app.Application;
import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Consumer;

import by.bstu.vs.stpms.lablist.model.entity.Lab;
import by.bstu.vs.stpms.lablist.model.repository.LabRepository;
import by.bstu.vs.stpms.lablist.ui.AbstractCrudViewModel;
import lombok.Getter;

public class LabViewModel extends AbstractCrudViewModel<Lab, LabRepository> {

    private int subjectId;
    private boolean createMode = true;
    private Consumer<SQLiteException> onError;

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

    public void setLabLiveData(Lab lab) {
        if (lab == null) {
            createMode = true;
            lab = new Lab();
        } else {
            createMode = false;
        }
        this.labLiveData = new MutableLiveData<>(lab);
    }

    public void setOnError(Consumer<SQLiteException> onError) {
        this.onError = onError;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void save() {
        labLiveData.getValue().setSubjectId(subjectId);
        if (createMode) add(labLiveData.getValue(), onError);
        else update(labLiveData.getValue(), onError);
    }

}