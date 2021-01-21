package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Supplier;

import by.bstu.vs.stpms.lablistsqlite.model.dao.LabDao;
import by.bstu.vs.stpms.lablistsqlite.model.dao.impl.LabDaoImpl;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;
import by.bstu.vs.stpms.lablistsqlite.model.repository.async.QueryAsyncTask;

public class LabRepository extends Repository<Lab> {

    private LabDao dao;

    private MutableLiveData<List<Lab>> labsBySubjectId;
    private MutableLiveData<Lab> item;
    private int currentId = 0;
    private boolean isLastDefault = true;


    public LabRepository(Context context) {
        super(context);
        dao = new LabDaoImpl(database);
        labsBySubjectId = new MutableLiveData<>();
        item = new MutableLiveData<>();
    }

    public LiveData<List<Lab>> getLabsBySubjectId(int subjectId) {
        currentId = subjectId;
        new QueryAsyncTask<>(labsBySubjectId, () -> dao.getLabsBySubjectId(currentId)).execute();
        isLastDefault = true;
        return labsBySubjectId;
    }

    public LiveData<List<Lab>> getLabsBySubjectIdSortedByState(int subjectId) {
        currentId = subjectId;
        new QueryAsyncTask<>(labsBySubjectId, () -> dao.getLabsBySubjectIdSortedByState(currentId)).execute();
        isLastDefault = false;
        return labsBySubjectId;
    }

    public LiveData<Lab> getById(int id) {
        new QueryAsyncTask<>(item, () -> dao.getById(id)).execute();
        return item;
    }

    @Override
    protected void refresh() {
        Supplier<List<Lab>> supplier;
        if(isLastDefault) supplier = () -> dao.getLabsBySubjectId(currentId);
        else supplier = () -> dao.getLabsBySubjectIdSortedByState(currentId);
        new QueryAsyncTask<>(labsBySubjectId, supplier).execute();
    }

}
