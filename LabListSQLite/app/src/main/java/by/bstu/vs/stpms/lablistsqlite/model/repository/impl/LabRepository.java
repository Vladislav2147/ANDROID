package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Supplier;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.bstu.vs.stpms.lablistsqlite.model.dao.LabDao;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
@SuppressLint("CheckResult")
public class LabRepository extends Repository<Lab, LabDao> {

    private MutableLiveData<List<Lab>> labsBySubjectId;
    private MutableLiveData<Lab> item;
    private int currentId = 0;
    private boolean isLastDefault = true;

    @Inject
    public LabRepository(LabDao dao) {
        super(dao);
        labsBySubjectId = new MutableLiveData<>();
        item = new MutableLiveData<>();
    }

    public LiveData<List<Lab>> getLabsBySubjectId(int subjectId) {
        currentId = subjectId;
        Single
                .create((SingleOnSubscribe<List<Lab>>) emitter -> emitter.onSuccess(dao.getLabsBySubjectId(currentId)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(labs -> labsBySubjectId.postValue(labs));
        isLastDefault = true;
        return labsBySubjectId;
    }

    public LiveData<List<Lab>> getLabsBySubjectIdSortedByState(int subjectId) {
        currentId = subjectId;
        Single
                .create((SingleOnSubscribe<List<Lab>>) emitter -> emitter.onSuccess(dao.getLabsBySubjectIdSortedByState(currentId)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(labs -> labsBySubjectId.postValue(labs));
        isLastDefault = false;
        return labsBySubjectId;
    }

    public LiveData<Lab> getById(int id) {
        Single
                .create((SingleOnSubscribe<Lab>) emitter -> emitter.onSuccess(dao.getById(id)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lab -> item.postValue(lab));
        return item;
    }

    @Override
    protected void refresh() {
        Supplier<List<Lab>> supplier;
        supplier = () -> isLastDefault ? dao.getLabsBySubjectId(currentId) :
                dao.getLabsBySubjectIdSortedByState(currentId);
        Single
                .create((SingleOnSubscribe<List<Lab>>) emitter -> emitter.onSuccess(supplier.get()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(labs -> labsBySubjectId.postValue(labs));
    }

}
