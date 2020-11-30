package by.bstu.vs.stpms.lablist.model.repository;

import android.app.Application;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.function.Consumer;

import by.bstu.vs.stpms.lablist.model.LabDatabase;
import by.bstu.vs.stpms.lablist.model.dao.SubjectDao;
import by.bstu.vs.stpms.lablist.model.entity.Subject;

public class SubjectRepository implements Repository<Subject> {

    private SubjectDao subjectDao;

    private LiveData<List<Subject>> subjectsByTermId;
    private LiveData<Subject> subject;

    public SubjectRepository(Application application) {
        LabDatabase db = LabDatabase.getDatabase(application);
        subjectDao = db.getSubjectDao();
    }

    public LiveData<List<Subject>> getSubjectsByTermId(int termId) {
        subjectsByTermId = subjectDao.getAllByTermId(termId);
        return subjectsByTermId;
    }

    @Override
    public void insert(Subject subject, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(subjectDao, onError, SubjectDao::insert).execute(subject);
    }

    @Override
    public void update(Subject subject, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(subjectDao, onError, SubjectDao::update).execute(subject);
    }

    @Override
    public void delete(Subject subject, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(subjectDao, onError, SubjectDao::delete).execute(subject);
    }
}
