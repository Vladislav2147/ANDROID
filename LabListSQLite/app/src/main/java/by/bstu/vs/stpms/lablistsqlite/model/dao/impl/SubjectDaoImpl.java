package by.bstu.vs.stpms.lablistsqlite.model.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.logging.FileLog;
import by.bstu.vs.stpms.lablistsqlite.model.dao.SubjectDao;
import by.bstu.vs.stpms.lablistsqlite.model.database.DatabaseContract;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Subject;

public class SubjectDaoImpl implements SubjectDao {

    private final static String TAG = "SubjectDaoImpl";
    private SQLiteDatabase database;

    public SubjectDaoImpl(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void insert(Subject item) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.SubjectTable.COLUMN_ID, item.getId());
        cv.put(DatabaseContract.SubjectTable.COLUMN_NAME, item.getName());
        cv.put(DatabaseContract.SubjectTable.COLUMN_TERM_ID, item.getTermId());

        try {
            if (database.insertOrThrow(DatabaseContract.SubjectTable.TABLE_NAME, null, cv) == -1) {
                SQLiteException exception = new SQLiteException("Subject insert failed");
                FileLog.getInstance().error(TAG, "insert: Subject " + item + " insert failed", exception);
                throw exception;
            }
            FileLog.getInstance().info(TAG, "insert: success " + item);
        } catch (Exception e) {
            FileLog.getInstance().error(TAG, "insert: Subject " + item + " insert failed", e);
            throw new SQLiteException("Subject insert failed");
        }
    }

    @Override
    public void delete(Subject item) {
        int deleted = database.delete(
                DatabaseContract.SubjectTable.TABLE_NAME,
                "id == ?",
                new String[]{item.getId().toString()}
        );

        if (deleted == 0) {
            SQLiteException exception = new SQLiteException("Subject wasn't deleted");
            FileLog.getInstance().error(TAG, "delete: Subject " + item + " wasn't deleted", exception);
            throw exception;
        }
        FileLog.getInstance().info(TAG, "delete: success " + item);

    }

    @Override
    public void update(Subject item) {

        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.SubjectTable.COLUMN_ID, item.getId());
        cv.put(DatabaseContract.SubjectTable.COLUMN_NAME, item.getName());
        cv.put(DatabaseContract.SubjectTable.COLUMN_TERM_ID, item.getTermId());

        int updated = database.update(
                DatabaseContract.SubjectTable.TABLE_NAME,
                cv,
                "id == ?",
                new String[]{item.getId().toString()}
        );

        if (updated == 0) {
            SQLiteException exception = new SQLiteException("Subject wasn't updated");
            FileLog.getInstance().error(TAG, "update: Subject " + item + " wasn't updated", exception);
            throw exception;
        }
        FileLog.getInstance().info(TAG, "update: success " + item);

    }

    @Override
    public List<Subject> getAllByTermId(int termId) {

        List<Subject> subjects = new ArrayList<>();

        Cursor cursor = database.query(
                DatabaseContract.SubjectTable.TABLE_NAME,
                null,
                DatabaseContract.SubjectTable.COLUMN_TERM_ID + " == ?",
                new String[]{String.valueOf(termId)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                subjects.add(getByCursor(cursor));
            } while (cursor.moveToNext());
        }
        FileLog.getInstance().info(TAG, "getAllByTermId: id = " + termId + "; rows = " + subjects.size());
        return subjects;
    }

    @Override
    public Subject getByCursor(Cursor cursor) {

        Subject subject = new Subject();

        int id = cursor.getColumnIndex(DatabaseContract.SubjectTable.COLUMN_ID);
        int name = cursor.getColumnIndex(DatabaseContract.SubjectTable.COLUMN_NAME);
        int termId = cursor.getColumnIndex(DatabaseContract.SubjectTable.COLUMN_TERM_ID);

        subject.setId(cursor.getInt(id));
        subject.setName(cursor.getString(name));
        subject.setTermId(cursor.getInt(termId));

        return subject;

    }
}
