package by.bstu.vs.stpms.lablist.model.repository;

import android.app.Application;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.function.Consumer;

import by.bstu.vs.stpms.lablist.model.LabDatabase;
import by.bstu.vs.stpms.lablist.model.dao.TermDao;
import by.bstu.vs.stpms.lablist.model.entity.Term;
import lombok.Getter;

public class TermRepository implements Repository<Term> {

    private TermDao termDao;

    @Getter
    private LiveData<List<Term>> terms;
    private LiveData<Term> term;


    public TermRepository(Application application) {
        LabDatabase db = LabDatabase.getDatabase(application);
        termDao = db.getTermDao();
        terms = termDao.getAll();
    }

    @Override
    public void insert(Term term, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(termDao, onError, TermDao::insert).execute(term);
    }

    @Override
    public void update(Term term, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(termDao, onError, TermDao::update).execute(term);
    }

    @Override
    public void delete(Term term, Consumer<SQLiteException> onError) {
        new DBAsyncTask<>(termDao, onError, TermDao::delete).execute(term);
    }
}
