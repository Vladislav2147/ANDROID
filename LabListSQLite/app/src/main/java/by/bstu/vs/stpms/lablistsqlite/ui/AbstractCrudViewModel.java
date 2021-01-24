package by.bstu.vs.stpms.lablistsqlite.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.dao.Dao;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Entity;
import by.bstu.vs.stpms.lablistsqlite.model.repository.Repository;
import io.reactivex.Completable;


public abstract class AbstractCrudViewModel<E extends Entity, R extends Repository<E, ? extends Dao<E>>> extends ViewModel {

    protected LiveData<List<E>> listLiveData;
    protected R repository;

    public AbstractCrudViewModel(R repository) {
        super();
        this.repository = repository;
    }

    public LiveData<List<E>> getItems() {
        return listLiveData;
    }

    public Completable add(E item) {
        return repository.insert(item);
    }

    public Completable update(E item) {
        return repository.update(item);
    }

    public Completable delete(E item) {
        return repository.delete(item);
    }
}
