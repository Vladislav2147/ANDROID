package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Consumer;

import by.bstu.vs.stpms.lablistsqlite.model.dao.LabDao;
import by.bstu.vs.stpms.lablistsqlite.model.dao.impl.LabDaoImpl;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;
import by.bstu.vs.stpms.lablistsqlite.model.repository.async.OperationAsyncTask;
import by.bstu.vs.stpms.lablistsqlite.model.repository.async.QueryAsyncTask;

public class LabRepository extends Repository<Lab> {

    private LabDao labDao;

    private MutableLiveData<List<Lab>> labsBySubjectId;
    private MutableLiveData<Lab> lab;


    public LabRepository(Context context) {
        super(context);
        labDao = new LabDaoImpl(database);
        labsBySubjectId = new MutableLiveData<>();
        lab = new MutableLiveData<>();
    }

    public LiveData<List<Lab>> getLabsBySubjectId(int subjectId) {
        new QueryAsyncTask<>(labsBySubjectId, () -> labDao.getLabsBySubjectId(subjectId)).execute();
        return labsBySubjectId;
    }

    public LiveData<Lab> getById(int id) {
        new QueryAsyncTask<>(lab, () -> labDao.getById(id)).execute();
        return lab;
    }

    @Override
    public void insert(Lab lab, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(labDao, onError, LabDao::insert).execute(lab);
    }

    @Override
    public void update(Lab lab, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(labDao, onError, LabDao::update).execute(lab);
    }

    @Override
    public void delete(Lab lab, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(labDao, onError, LabDao::delete).execute(lab);
    }

}
