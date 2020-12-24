package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.content.Context;
import android.database.sqlite.SQLiteException;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Consumer;

import by.bstu.vs.stpms.lablistsqlite.model.dao.TermDao;
import by.bstu.vs.stpms.lablistsqlite.model.dao.impl.TermDaoImpl;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Term;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;
import by.bstu.vs.stpms.lablistsqlite.model.repository.async.OperationAsyncTask;
import by.bstu.vs.stpms.lablistsqlite.model.repository.async.QueryAsyncTask;
import lombok.Getter;

public class TermRepository extends Repository<Term> {

    private TermDao termDao;

    @Getter
    private MutableLiveData<List<Term>> terms;

    public TermRepository(Context context) {
        super(context);
        termDao = new TermDaoImpl(database);
        terms = new MutableLiveData<>();
        refresh();
    }

    @Override
    public void insert(Term term, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(termDao, onError, TermDao::insert).execute(term);
        refresh();
    }

    @Override
    public void update(Term term, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(termDao, onError, TermDao::update).execute(term);
        refresh();
    }

    @Override
    public void delete(Term term, Consumer<SQLiteException> onError) {
        new OperationAsyncTask<>(termDao, onError, TermDao::delete).execute(term);
        refresh();
    }

    @Override
    protected void refresh() {
        new QueryAsyncTask<>(terms, () -> termDao.getAll()).execute();
    }
}
