package by.bstu.vs.stpms.lablist.model.repository;

import android.database.sqlite.SQLiteException;

import java.util.function.Consumer;

public interface Repository<T> {

    void insert(T item, Consumer<SQLiteException> onError);

    void update(T item, Consumer<SQLiteException> onError);

    void delete(T item, Consumer<SQLiteException> onError);
}
