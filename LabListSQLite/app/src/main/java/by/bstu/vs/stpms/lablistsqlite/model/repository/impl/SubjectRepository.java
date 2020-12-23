package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Consumer;

import by.bstu.vs.stpms.lablistsqlite.model.dao.SubjectDao;
import by.bstu.vs.stpms.lablistsqlite.model.dao.impl.SubjectDaoImpl;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Subject;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;
import by.bstu.vs.stpms.lablistsqlite.model.repository.async.OperationAsyncTask;
import by.bstu.vs.stpms.lablistsqlite.model.repository.async.QueryAsyncTask;

public class SubjectRepository extends Repository<Subject> {

    private SubjectDao subjectDao;

    private MutableLiveData<List<Subject>> subjectsByTermId;
    private LiveData<Subject> subject;

    public SubjectRepository(Context context) {
        super(context);
        subjectDao = new SubjectDaoImpl(database);
        subjectsByTermId = new MutableLiveData<>();
    }

    public LiveData<List<Subject>> getSubjectsByTermId(int termId) {
        new QueryAsyncTask<>(subjectsByTermId, () -> subjectDao.getAllByTermId(termId)).execute();
        return subjectsByTermId;
    }

    @Override
    public void insert(Subject subject, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(subjectDao, onError, SubjectDao::insert).execute(subject);
    }

    @Override
    public void update(Subject subject, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(subjectDao, onError, SubjectDao::update).execute(subject);
    }

    @Override
    public void delete(Subject subject, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(subjectDao, onError, SubjectDao::delete).execute(subject);
    }
}
