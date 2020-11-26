package by.bstu.vs.stpms.lablist.model.repository;

import android.app.Application;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
        term = termDao.getById(id);
        return term;
    }

    public void insertTerm(Term term, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(termDao, onError, TermDao::insert).execute(term);
    }

    public void updateTerm(Term term, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(termDao, onError, (BiConsumer<TermDao, Term>) TermDao::update).execute(term);
    }

    public void deleteTerm(Term term, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(termDao, onError, (BiConsumer<TermDao, Term>) TermDao::delete).execute(term);
    }
}
