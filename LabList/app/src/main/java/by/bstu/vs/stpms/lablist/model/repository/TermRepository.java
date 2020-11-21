package by.bstu.vs.stpms.lablist.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.bstu.vs.stpms.lablist.model.LabDatabase;
import by.bstu.vs.stpms.lablist.model.dao.TermDao;
import by.bstu.vs.stpms.lablist.model.entity.Term;
import lombok.Getter;

public class TermRepository {

    private TermDao termDao;

    @Getter
    private LiveData<List<Term>> terms;
    private LiveData<Term> term;


    public TermRepository(Application application) {
        LabDatabase db = LabDatabase.getDatabase(application);
        termDao = db.getTermDao();
        terms = termDao.getAll();
    }

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
