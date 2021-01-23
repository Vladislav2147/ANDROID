package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.dao.SubjectDao;
import by.bstu.vs.stpms.lablistsqlite.model.dao.impl.SubjectDaoImpl;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Subject;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class SubjectRepository extends Repository<Subject, SubjectDao> {

    private MutableLiveData<List<Subject>> subjectsByTermId;
    private int currentId = 0;

    public SubjectRepository(Context context) {
        super(context);
        dao = new SubjectDaoImpl(database);
        subjectsByTermId = new MutableLiveData<>();
    }

    public LiveData<List<Subject>> getSubjectsByTermId(int termId) {
        currentId = termId;
        Single
                .create((SingleOnSubscribe<List<Subject>>) emitter -> emitter.onSuccess(dao.getAllByTermId(termId)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subjects -> subjectsByTermId.postValue(subjects));
        return subjectsByTermId;
    }

    @Override
    protected void refresh() {
        Single
                .create((SingleOnSubscribe<List<Subject>>) emitter -> emitter.onSuccess(dao.getAllByTermId(currentId)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subjects -> subjectsByTermId.postValue(subjects));
    }
}
