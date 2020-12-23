package by.bstu.vs.stpms.lablistsqlite.model.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.function.Consumer;

import by.bstu.vs.stpms.lablistsqlite.model.database.DatabaseOpenHelper;

public abstract class Repository<E> {

    protected SQLiteDatabase database;

    public Repository(Context context) {
        DatabaseOpenHelper openHelper = new DatabaseOpenHelper(context);
        database = openHelper.getReadableDatabase();
    }

    public abstract void insert(E item, Consumer<SQLiteException> onError);

    public abstract void update(E item, Consumer<SQLiteException> onError);

    public abstract void delete(E item, Consumer<SQLiteException> onError);
}
