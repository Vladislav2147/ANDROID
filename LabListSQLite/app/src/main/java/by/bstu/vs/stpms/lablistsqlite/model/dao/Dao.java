package by.bstu.vs.stpms.lablistsqlite.model.dao;

import android.database.Cursor;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Entity;

public interface Dao<T extends Entity > {

    void insert(T item);
    void delete(T item);
    void update(T item);

    T getByCursor(Cursor cursor);
}
