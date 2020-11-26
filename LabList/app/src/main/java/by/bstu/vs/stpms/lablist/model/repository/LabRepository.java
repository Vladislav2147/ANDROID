package by.bstu.vs.stpms.lablist.model.repository;

import android.app.Application;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.function.Consumer;

import by.bstu.vs.stpms.lablist.model.LabDatabase;
import by.bstu.vs.stpms.lablist.model.dao.LabDao;
import by.bstu.vs.stpms.lablist.model.entity.Lab;

public class LabRepository implements Repository<Lab> {

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

    public LiveData<Lab> getById(int id) {
        lab = labDao.getById(id);
        return lab;
    }

    public void insert(Lab lab, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(labDao, onError, LabDao::insert).execute(lab);
    }

    public void update(Lab lab, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(labDao, onError, LabDao::update).execute(lab);
    }

    public void delete(Lab lab, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(labDao, onError, LabDao::delete).execute(lab);
    }

}
