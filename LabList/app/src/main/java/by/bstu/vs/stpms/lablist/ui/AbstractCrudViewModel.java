package by.bstu.vs.stpms.lablist.ui;

import android.app.Application;
import android.database.sqlite.SQLiteException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.function.Consumer;

import by.bstu.vs.stpms.lablist.model.repository.Repository;

public abstract class AbstractCrudViewModel<E, R extends Repository<E>> extends AndroidViewModel {

    protected LiveData<List<E>> listLiveData;
    protected R repository;

    public AbstractCrudViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<E>> getTerms() {
        return listLiveData;
    }

    public LiveData<E> getById(int id) {
        return repository.getById(id);
    }

    public void add(E item, Consumer<SQLiteException> onError) {
        repository.insert(item, onError);
    }

    public void update(E item, Consumer<SQLiteException> onError) {
        repository.update(item, onError);
    }

    public void delete(E item, Consumer<SQLiteException> onError) {
        repository.delete(item, onError);
    }
}
