package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.function.Consumer;

import by.bstu.vs.stpms.lablistsqlite.model.LabDatabase;
import by.bstu.vs.stpms.lablistsqlite.model.dao.SubjectDao;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Subject;
import by.bstu.vs.stpms.lablistsqlite.model.repository.async.OperationAsyncTask;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;

public class SubjectRepository extends Repository<Subject> {

    private SubjectDao subjectDao;

    private LiveData<List<Subject>> subjectsByTermId;
    private LiveData<Subject> subject;

    public SubjectRepository(Context context) {
        super(context);
    }

    public LiveData<List<Subject>> getSubjectsByTermId(int termId) {
        subjectsByTermId = subjectDao.getAllByTermId(termId);
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
