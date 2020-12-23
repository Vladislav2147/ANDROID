package by.bstu.vs.stpms.lablistsqlite.model.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.function.Consumer;

import by.bstu.vs.stpms.lablistsqlite.model.database.DatabaseOpenHelper;

public abstract class Repository<T> {

    protected SQLiteDatabase database;

    public Repository(Context context) {
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(context);
        database = openHelper.getReadableDatabase();
    }

    public abstract void insert(T item, Consumer<SQLiteException> onError);

    public abstract void update(T item, Consumer<SQLiteException> onError);

    public abstract void delete(T item, Consumer<SQLiteException> onError);
}
