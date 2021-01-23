package by.bstu.vs.stpms.lablistsqlite.model.dao;

import android.database.sqlite.SQLiteException;

import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;

public interface LabDao extends Dao<Lab> {
    Lab getById(int id) throws SQLiteException;
    List<Lab> getLabsBySubjectId(int subjectId);
    List<Lab> getLabsBySubjectIdSortedByState(int subjectId);
}
