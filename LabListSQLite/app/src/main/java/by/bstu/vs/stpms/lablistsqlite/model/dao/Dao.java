package by.bstu.vs.stpms.lablistsqlite.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Entity;

public interface Dao<T extends Entity > {

    void insert(T item) throws SQLiteException;
    void delete(T item) throws SQLiteException;
    void update(T item) throws SQLiteException;

    T getByCursor(Cursor cursor);
}
