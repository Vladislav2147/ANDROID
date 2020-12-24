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
            SQLiteException exception = new SQLiteException("Lab insert failed");
            FileLog.getInstance().error(TAG, "insert: Lab " + lab + " insert failed", exception);
            throw exception;
        }
        FileLog.getInstance().info(TAG, "insert: success " + lab);
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
    public void delete(Lab lab) {
        int deleted = database.delete(
                DatabaseContract.LabTable.TABLE_NAME,
                "id == ?",
                new String[]{lab.getId().toString()}
        );

        if (deleted == 0) {
            SQLiteException exception = new SQLiteException("Lab wasn't deleted");
            FileLog.getInstance().error(TAG, "delete: Lab " + lab + " wasn't deleted", exception);
            throw exception;
        }
        FileLog.getInstance().info(TAG, "delete: success " + lab);
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
            SQLiteException exception = new SQLiteException("Lab wasn't updated");
            FileLog.getInstance().error(TAG, "update: Lab " + lab + " wasn't updated", exception);
            throw exception;
        }
        FileLog.getInstance().info(TAG, "update: success " + lab);
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
        FileLog.getInstance().info(TAG, "getLabsByStateAndSubjectId: id = " + subjectId + "; rows = " + labs.size());
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
