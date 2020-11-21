package by.bstu.vs.stpms.lablist.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.vs.stpms.lablist.model.LabDatabase;
import by.bstu.vs.stpms.lablist.model.dao.LabDao;
import by.bstu.vs.stpms.lablist.model.dao.SubjectDao;
import by.bstu.vs.stpms.lablist.model.dao.TermDao;
import by.bstu.vs.stpms.lablist.model.entity.Lab;
import by.bstu.vs.stpms.lablist.model.entity.Subject;
import by.bstu.vs.stpms.lablist.model.entity.Term;
import lombok.Getter;

public class LabsRepository {

    private LabDao labDao;
    private SubjectDao subjectDao;
    private TermDao termDao;

    private LiveData<List<Lab>> labsBySubjectId;
    private LiveData<List<Subject>> subjectsByTermId;
    @Getter
    private LiveData<List<Term>> terms;

    private LiveData<Lab> lab;
    private LiveData<Subject> subject;
    private LiveData<Term> term;

    public LabsRepository(Application application) {
        LabDatabase db = LabDatabase.getDatabase(application);
        labDao = db.getLabDao();
        subjectDao = db.getSubjectDao();
        termDao = db.getTermDao();

        terms = termDao.getAll();
    }

    /*

        LAB

    */

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

    /*

        SUBJECT

    */

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

    /*

        TERM

    */

    public LiveData<Term> getTermById(int id) {
        LabDatabase.databaseWriteExecutor.execute(() -> term = termDao.getById(id));
        return term;
    }

    public void insertTerm(Term term) {
        LabDatabase.databaseWriteExecutor.execute(() -> termDao.insert(term));
    }

    public void updateTerm(Term term) {
        LabDatabase.databaseWriteExecutor.execute(() -> termDao.update(term));
    }

    public void deleteTerm(Term term) {
        LabDatabase.databaseWriteExecutor.execute(() -> termDao.delete(term));
    }

}
