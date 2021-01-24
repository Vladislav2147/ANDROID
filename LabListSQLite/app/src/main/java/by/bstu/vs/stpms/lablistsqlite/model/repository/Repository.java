package by.bstu.vs.stpms.lablistsqlite.model.repository;

import android.database.sqlite.SQLiteException;

import by.bstu.vs.stpms.lablistsqlite.model.dao.Dao;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Entity;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;

public abstract class Repository<E extends Entity, D extends Dao<E>> {

    protected D dao;

    public Repository(D dao) {
        this.dao = dao;
    }

    public final Completable insert(E item) {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver observer) {
                try {
                    dao.insert(item);
                    refresh();
                    observer.onComplete();
                } catch (SQLiteException e) {
                    observer.onError(e);
                }
            }
        };
    }

    public final Completable update(E item) {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver observer) {
                try {
                    dao.update(item);
                    refresh();
                    observer.onComplete();
                } catch (SQLiteException e) {
                    observer.onError(e);
                }
            }
        };
    }
    public final Completable delete(E item) {
        return new Completable() {
            @Override
            protected void subscribeActual(CompletableObserver observer) {
                try {
                    dao.delete(item);
                    refresh();
                    observer.onComplete();
                } catch (SQLiteException e) {
                    observer.onError(e);
                }
            }
        };
    }

    protected abstract void refresh();
}
