package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
    private int currentId = 0;
    private boolean isLastDefault = true;


    public LabRepository(Context context) {
        super(context);
        labDao = new LabDaoImpl(database);
        labsBySubjectId = new MutableLiveData<>();
        lab = new MutableLiveData<>();
    }

    public LiveData<List<Lab>> getLabsBySubjectId(int subjectId) {
        currentId = subjectId;
        new QueryAsyncTask<>(labsBySubjectId, () -> labDao.getLabsBySubjectId(currentId)).execute();
        isLastDefault = true;
        return labsBySubjectId;
    }

    public LiveData<List<Lab>> getLabsBySubjectIdSortedByState(int subjectId) {
        currentId = subjectId;
        new QueryAsyncTask<>(labsBySubjectId, () -> labDao.getLabsBySubjectIdSortedByState(currentId)).execute();
        isLastDefault = false;
        return labsBySubjectId;
    }

    public LiveData<Lab> getById(int id) {
        new QueryAsyncTask<>(lab, () -> labDao.getById(id)).execute();
        return lab;
    }

    @Override
    public void insert(Lab lab, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(labDao, onError, LabDao::insert).execute(lab);
        refresh();
    }

    @Override
    public void update(Lab lab, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(labDao, onError, LabDao::update).execute(lab);
        refresh();
    }

    @Override
    public void delete(Lab lab, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(labDao, onError, LabDao::delete).execute(lab);
        refresh();
    }

    @Override
    protected void refresh() {
        Supplier<List<Lab>> supplier;
        if(isLastDefault) supplier = () -> labDao.getLabsBySubjectId(currentId);
        else supplier = () -> labDao.getLabsBySubjectIdSortedByState(currentId);
        new QueryAsyncTask<>(labsBySubjectId, supplier).execute();
    }

}
