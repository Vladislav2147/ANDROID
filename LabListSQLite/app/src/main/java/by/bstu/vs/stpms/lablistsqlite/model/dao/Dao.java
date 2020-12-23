package by.bstu.vs.stpms.lablistsqlite.model.dao;

import android.database.Cursor;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Entity;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;

public interface Dao<T extends Entity > {

    void insert(Lab lab);
    void delete(Lab lab);
    void update(Lab lab);

    T getByCursor(Cursor cursor);
}
