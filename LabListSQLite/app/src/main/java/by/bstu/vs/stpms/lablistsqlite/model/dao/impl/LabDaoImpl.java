package by.bstu.vs.stpms.lablistsqlite.model.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.model.dao.LabDao;
import by.bstu.vs.stpms.lablistsqlite.model.database.DatabaseContract;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;

public class LabDaoImpl implements LabDao {

    private SQLiteDatabase database;

    public LabDaoImpl(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void insert(Lab lab) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.LabTable.COLUMN_ID, lab.getId());
        cv.put(DatabaseContract.LabTable.COLUMN_NAME, lab.getName());
        cv.put(DatabaseContract.LabTable.COLUMN_SUBJECT_ID, lab.getSubjectId());
        cv.put(DatabaseContract.LabTable.COLUMN_TASK_PATH, lab.getTaskFilePath());
        cv.put(DatabaseContract.LabTable.COLUMN_CODE_REFERENCE, lab.getCodeReference());
        cv.put(DatabaseContract.LabTable.COLUMN_NOTES, lab.getNotes());
        cv.put(DatabaseContract.LabTable.COLUMN_IS_PASSED, lab.isPassed());

        if (database.insertOrThrow(DatabaseContract.LabTable.TABLE_NAME, null, cv) == -1) {
            throw new SQLiteException("Lab insert failed");
        }
    }

    @Override
    public Lab getById(int id) {
        Cursor cursor = database.query(
                DatabaseContract.LabTable.TABLE_NAME,
                null,
                "id == ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            return getByCursor(cursor);
        } else {
            throw new SQLiteException("Lab with id = " + id + " not found");
        }
    }

    @Override
    public void delete(Lab lab) {
        int deleted = database.delete(
                DatabaseContract.LabTable.TABLE_NAME,
                "id == ?",
                new String[]{lab.getId().toString()}
        );

        if (deleted == 0) {
            throw new SQLiteException("Lab wasn't deleted");
        }
    }

    @Override
    public void update(Lab lab) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.LabTable.COLUMN_ID, lab.getId());
        cv.put(DatabaseContract.LabTable.COLUMN_NAME, lab.getName());
        cv.put(DatabaseContract.LabTable.COLUMN_SUBJECT_ID, lab.getSubjectId());
        cv.put(DatabaseContract.LabTable.COLUMN_TASK_PATH, lab.getTaskFilePath());
        cv.put(DatabaseContract.LabTable.COLUMN_CODE_REFERENCE, lab.getCodeReference());
        cv.put(DatabaseContract.LabTable.COLUMN_NOTES, lab.getNotes());
        cv.put(DatabaseContract.LabTable.COLUMN_IS_PASSED, lab.isPassed());

        int updated = database.update(
                DatabaseContract.LabTable.TABLE_NAME,
                cv,
                "id == ?",
                new String[]{lab.getId().toString()}
        );

        if (updated == 0) {
            throw new SQLiteException("Lab wasn't updated");
        }
    }

    @Override
    public List<Lab> getLabsBySubjectId(int subjectId) {

        List<Lab> labs = new ArrayList<>();

        Cursor cursor = database.query(
                DatabaseContract.LabTable.TABLE_NAME,
                null,
                DatabaseContract.LabTable.COLUMN_SUBJECT_ID + " == ?",
                new String[]{String.valueOf(subjectId)},
                null,
                null,
                null
        );


        if (cursor.moveToFirst()) {
            do {
                labs.add(getByCursor(cursor));
            } while (cursor.moveToNext());
        }
        return labs;
    }

    @Override
    public List<Lab> getLabsByStateAndSubjectId(boolean isPassed, int subjectId) {
        List<Lab> labs = new ArrayList<>();

        Cursor cursor = database.query(
                DatabaseContract.LabTable.TABLE_NAME,
                null,
                DatabaseContract.LabTable.COLUMN_SUBJECT_ID + " == ? and " +
                        DatabaseContract.LabTable.COLUMN_IS_PASSED + " == ?",
                new String[]{String.valueOf(subjectId), String.valueOf(isPassed)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                labs.add(getByCursor(cursor));
            } while (cursor.moveToNext());
        }
        return labs;
    }

    @Override
    public Lab getByCursor(Cursor cursor) {

        Lab lab = new Lab();

        int id = cursor.getColumnIndex(DatabaseContract.LabTable.COLUMN_ID);
        int name = cursor.getColumnIndex(DatabaseContract.LabTable.COLUMN_NAME);
        int subjectId = cursor.getColumnIndex(DatabaseContract.LabTable.COLUMN_SUBJECT_ID);
        int taskFilePath = cursor.getColumnIndex(DatabaseContract.LabTable.COLUMN_TASK_PATH);
        int codeReference = cursor.getColumnIndex(DatabaseContract.LabTable.COLUMN_CODE_REFERENCE);
        int notes = cursor.getColumnIndex(DatabaseContract.LabTable.COLUMN_NOTES);
        int isPassed = cursor.getColumnIndex(DatabaseContract.LabTable.COLUMN_IS_PASSED);

        lab.setId(cursor.getInt(id));
        lab.setName(cursor.getString(name));
        lab.setSubjectId(cursor.getInt(subjectId));
        lab.setTaskFilePath(cursor.getString(taskFilePath));
        lab.setCodeReference(cursor.getString(codeReference));
        lab.setNotes(cursor.getString(notes));
        lab.setPassed(cursor.getInt(isPassed) > 0);

        return lab;

    }
}
