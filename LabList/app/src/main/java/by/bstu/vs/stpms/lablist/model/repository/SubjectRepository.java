package by.bstu.vs.stpms.lablist.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.vs.stpms.lablist.model.LabDatabase;
import by.bstu.vs.stpms.lablist.model.dao.SubjectDao;
import by.bstu.vs.stpms.lablist.model.entity.Subject;

public class SubjectRepository {

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

    public LiveData<Subject> getSubjectById(int id) {
        LabDatabase.databaseWriteExecutor.execute(() -> subject = subjectDao.getById(id));
        return subject;
    }

    public void insertSubject(Subject subject) {
        LabDatabase.databaseWriteExecutor.execute(() -> subjectDao.insert(subject));
    }

    public void updateSubject(Subject subject) {
        LabDatabase.databaseWriteExecutor.execute(() -> subjectDao.update(subject));
    }

    public void deleteSubject(Subject subject) {
        LabDatabase.databaseWriteExecutor.execute(() -> subjectDao.delete(subject));
    }
}
