package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.dao.TermDao;
import by.bstu.vs.stpms.lablistsqlite.model.dao.impl.TermDaoImpl;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Term;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;
import by.bstu.vs.stpms.lablistsqlite.model.repository.async.QueryAsyncTask;
import lombok.Getter;

public class TermRepository extends Repository<Term, TermDao> {

    @Getter
    private MutableLiveData<List<Term>> terms;

    public TermRepository(Context context) {
        super(context);
        dao = new TermDaoImpl(database);
        terms = new MutableLiveData<>();
        refresh();
    }

    @Override
    protected void refresh() {
        new QueryAsyncTask<>(terms, () -> dao.getAll()).execute();
    }
}
