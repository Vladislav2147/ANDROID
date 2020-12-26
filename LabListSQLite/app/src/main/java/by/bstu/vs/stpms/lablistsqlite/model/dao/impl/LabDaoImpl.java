package by.bstu.vs.stpms.lablistsqlite.model.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.logging.FileLog;
import by.bstu.vs.stpms.lablistsqlite.model.dao.LabDao;
import by.bstu.vs.stpms.lablistsqlite.model.database.DatabaseContract;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Lab;

public class LabDaoImpl implements LabDao {

    private final static String TAG = "LabDaoImpl";
    private SQLiteDatabase database;

    public LabDaoImpl(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void insert(Lab item) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.LabTable.COLUMN_ID, item.getId());
        cv.put(DatabaseContract.LabTable.COLUMN_NAME, item.getName());
        cv.put(DatabaseContract.LabTable.COLUMN_SUBJECT_ID, item.getSubjectId());
        cv.put(DatabaseContract.LabTable.COLUMN_TASK_PATH, item.getTaskFilePath());
        cv.put(DatabaseContract.LabTable.COLUMN_CODE_REFERENCE, item.getCodeReference());
        cv.put(DatabaseContract.LabTable.COLUMN_NOTES, item.getNotes());
        cv.put(DatabaseContract.LabTable.COLUMN_IS_PASSED, item.isPassed());

        try {
            if (database.insertOrThrow(DatabaseContract.LabTable.TABLE_NAME, null, cv) == -1) {
                SQLiteException exception = new SQLiteException("Lab insert failed");
                FileLog.getInstance().error(TAG, "insert: Lab " + item + " insert failed", exception);
                throw exception;
            }
            FileLog.getInstance().info(TAG, "insert: success " + item);
        } catch (Exception e) {
            FileLog.getInstance().error(TAG, "insert: Lab " + item + " insert failed", e);
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
            FileLog.getInstance().info(TAG, "getById: success id = " + id);
            return getByCursor(cursor);
        } else {
            SQLiteException exception = new SQLiteException("Lab with id = " + id + " not found");
            FileLog.getInstance().error(TAG, "getById: Lab with id = " + id + " not found", exception);
            throw exception;
        }
    }

    @Override
    public void delete(Lab item) {
        int deleted = database.delete(
                DatabaseContract.LabTable.TABLE_NAME,
                "id == ?",
                new String[]{item.getId().toString()}
        );

        if (deleted == 0) {
            SQLiteException exception = new SQLiteException("Lab wasn't deleted");
            FileLog.getInstance().error(TAG, "delete: Lab " + item + " wasn't deleted", exception);
            throw exception;
        }
        FileLog.getInstance().info(TAG, "delete: success " + item);
    }

    @Override
    public void update(Lab item) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.LabTable.COLUMN_ID, item.getId());
        cv.put(DatabaseContract.LabTable.COLUMN_NAME, item.getName());
        cv.put(DatabaseContract.LabTable.COLUMN_SUBJECT_ID, item.getSubjectId());
        cv.put(DatabaseContract.LabTable.COLUMN_TASK_PATH, item.getTaskFilePath());
        cv.put(DatabaseContract.LabTable.COLUMN_CODE_REFERENCE, item.getCodeReference());
        cv.put(DatabaseContract.LabTable.COLUMN_NOTES, item.getNotes());
        cv.put(DatabaseContract.LabTable.COLUMN_IS_PASSED, item.isPassed());

        int updated = database.update(
                DatabaseContract.LabTable.TABLE_NAME,
                cv,
                "id == ?",
                new String[]{item.getId().toString()}
        );

        if (updated == 0) {
            SQLiteException exception = new SQLiteException("Lab wasn't updated");
            FileLog.getInstance().error(TAG, "update: Lab " + item + " wasn't updated", exception);
            throw exception;
        }
        FileLog.getInstance().info(TAG, "update: success " + item);
    }

    @Override
    public List<Lab> getLabsBySubjectIdSortedByState(int subjectId) {

        List<Lab> labs = new ArrayList<>();

        Cursor cursor = database.query(
                DatabaseContract.LabTable.TABLE_NAME,
                null,
                DatabaseContract.LabTable.COLUMN_SUBJECT_ID + " == ?",
                new String[]{String.valueOf(subjectId)},
                null,
                null,
                DatabaseContract.LabTable.COLUMN_IS_PASSED
        );


        if (cursor.moveToFirst()) {
            do {
                labs.add(getByCursor(cursor));
            } while (cursor.moveToNext());
        }
        FileLog.getInstance().info(TAG, "getLabsBySubjectIdSortedByState: id = " + subjectId + "; rows = " + labs.size());
        return labs;
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
        FileLog.getInstance().info(TAG, "getLabsBySubjectId: id = " + subjectId + "; rows = " + labs.size());
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
