package by.bstu.vs.stpms.lablist.model.repository;

import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;

import java.util.function.Consumer;

public interface Repository<T> {

    LiveData<T> getById(int id);

    void insert(T item, Consumer<SQLiteException> onError);

    void update(T item, Consumer<SQLiteException> onError);

    void delete(T item, Consumer<SQLiteException> onError);
}
