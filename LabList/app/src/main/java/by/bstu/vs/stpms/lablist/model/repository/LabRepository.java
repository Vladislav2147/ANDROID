package by.bstu.vs.stpms.lablist.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.vs.stpms.lablist.model.LabDatabase;
import by.bstu.vs.stpms.lablist.model.dao.LabDao;
import by.bstu.vs.stpms.lablist.model.entity.Lab;

public class LabRepository {

    private LabDao labDao;

    private LiveData<List<Lab>> labsBySubjectId;
    private LiveData<Lab> lab;


    public LabRepository(Application application) {
        LabDatabase db = LabDatabase.getDatabase(application);
        labDao = db.getLabDao();
    }

    public LiveData<List<Lab>> getLabsBySubjectId(int subjectId) {
        labsBySubjectId = labDao.getLabsBySubjectId(subjectId);
        return labsBySubjectId;
    }

    public LiveData<Lab> getLabById(int id) {
        LabDatabase.databaseWriteExecutor.execute(() -> lab = labDao.getById(id));
        return lab;
    }

    public void insertLab(Lab lab) {
        LabDatabase.databaseWriteExecutor.execute(() -> labDao.insert(lab));
    }

    public void updateLab(Lab lab) {
        LabDatabase.databaseWriteExecutor.execute(() -> labDao.update(lab));
    }

    public void deleteLab(Lab lab) {
        LabDatabase.databaseWriteExecutor.execute(() -> labDao.delete(lab));
    }

}
