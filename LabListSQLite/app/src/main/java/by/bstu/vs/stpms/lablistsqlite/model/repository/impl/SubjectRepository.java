package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.dao.SubjectDao;
import by.bstu.vs.stpms.lablistsqlite.model.dao.impl.SubjectDaoImpl;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Subject;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;
import by.bstu.vs.stpms.lablistsqlite.model.repository.async.QueryAsyncTask;

public class SubjectRepository extends Repository<Subject, SubjectDao> {

    private MutableLiveData<List<Subject>> subjectsByTermId;
    private int currentId = 0;

    public SubjectRepository(Context context) {
        super(context);
        dao = new SubjectDaoImpl(database);
        subjectsByTermId = new MutableLiveData<>();
    }

    public LiveData<List<Subject>> getSubjectsByTermId(int termId) {
        currentId = termId;
        new QueryAsyncTask<>(subjectsByTermId, () -> dao.getAllByTermId(currentId)).execute();
        return subjectsByTermId;
    }

    @Override
    protected void refresh() {
        new QueryAsyncTask<>(subjectsByTermId, () -> dao.getAllByTermId(currentId)).execute();
    }
}
