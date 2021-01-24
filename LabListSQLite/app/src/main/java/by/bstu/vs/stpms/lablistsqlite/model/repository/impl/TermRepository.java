package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.bstu.vs.stpms.lablistsqlite.model.dao.TermDao;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Term;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.Getter;

@Singleton
@SuppressLint("CheckResult")
public class TermRepository extends Repository<Term, TermDao> {

    @Getter
    private MutableLiveData<List<Term>> terms;

    @Inject
    public TermRepository(TermDao dao) {
        super(dao);
        terms = new MutableLiveData<>();
        refresh();
    }

    @Override
    protected void refresh() {
        Single
                .create((SingleOnSubscribe<List<Term>>) emitter -> emitter.onSuccess(dao.getAll()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(terms -> this.terms.postValue(terms));
    }
}
