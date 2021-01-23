package by.bstu.vs.stpms.lablistsqlite.model.repository.impl;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.function.Supplier;

import by.bstu.vs.stpms.lablistsqlite.model.dao.LabDao;
import by.bstu.vs.stpms.lablistsqlite.model.dao.impl.LabDaoImpl;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;
import by.bstu.vs.stpms.lablistsqlite.model.repository.async.QueryAsyncTask;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressLint("CheckResult")
public class LabRepository extends Repository<Lab, LabDao> {

    private MutableLiveData<List<Lab>> labsBySubjectId;
    private MutableLiveData<Lab> item;
    private int currentId = 0;
    private boolean isLastDefault = true;


    public LabRepository(Context context) {
        super(context);
        dao = new LabDaoImpl(database);
        labsBySubjectId = new MutableLiveData<>();
        item = new MutableLiveData<>();
    }

    public LiveData<List<Lab>> getLabsBySubjectId(int subjectId) {
        currentId = subjectId;
        new QueryAsyncTask<>(labsBySubjectId, () -> dao.getLabsBySubjectId(currentId)).execute();
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
