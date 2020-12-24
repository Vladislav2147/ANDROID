package by.bstu.vs.stpms.lablistsqlite.model.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.logging.FileLog;
import by.bstu.vs.stpms.lablistsqlite.model.dao.TermDao;
import by.bstu.vs.stpms.lablistsqlite.model.database.DatabaseContract;
import by.bstu.vs.stpms.lablistsqlite.model.entity.Term;

public class TermDaoImpl implements TermDao {

    private final static String TAG = "TermDaoImpl";
    private SQLiteDatabase database;

    public TermDaoImpl(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void insert(Term item) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.TermTable.COLUMN_ID, item.getId());
        cv.put(DatabaseContract.TermTable.COLUMN_COURSE, item.getCourse());
        cv.put(DatabaseContract.TermTable.COLUMN_SEMESTER, item.getSemester());

        try {
            if (database.insertOrThrow(DatabaseContract.TermTable.TABLE_NAME, null, cv) == -1) {
                SQLiteException exception = new SQLiteException("Term insert failed");
                FileLog.getInstance().error(TAG, "insert: Term " + item + " insert failed", exception);
                throw exception;
            }
            FileLog.getInstance().info(TAG, "insert: success " + item);
        } catch (Exception e) {
            FileLog.getInstance().error(TAG, "insert: Term " + item + " insert failed", e);
            throw new SQLiteException("Term insert failed");
        }
    }

    @Override
    public void delete(Term item) {
        int deleted = database.delete(
                DatabaseContract.TermTable.TABLE_NAME,
                "id == ?",
                new String[]{item.getId().toString()}
        );

        if (deleted == 0) {
            SQLiteException exception = new SQLiteException("Term wasn't deleted");
            FileLog.getInstance().error(TAG, "delete: Term " + item + " wasn't deleted", exception);
            throw exception;
        }
        FileLog.getInstance().info(TAG, "delete: success " + item);
    }

    @Override
    public void update(Term item) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.TermTable.COLUMN_ID, item.getId());
        cv.put(DatabaseContract.TermTable.COLUMN_COURSE, item.getCourse());
        cv.put(DatabaseContract.TermTable.COLUMN_SEMESTER, item.getSemester());

        int updated = database.update(
                DatabaseContract.TermTable.TABLE_NAME,
                cv,
                "id == ?",
                new String[]{item.getId().toString()}
        );

        if (updated == 0) {
            SQLiteException exception = new SQLiteException("Term wasn't updated");
            FileLog.getInstance().error(TAG, "update: Term " + item + " wasn't updated", exception);
            throw exception;
        }
        FileLog.getInstance().info(TAG, "update: success " + item);
    }

    @Override
    public List<Term> getAll() {

        List<Term> terms = new ArrayList<>();

        Cursor cursor = database.query(
                DatabaseContract.TermTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                terms.add(getByCursor(cursor));
            } while (cursor.moveToNext());
        }
        FileLog.getInstance().info(TAG, "getAll: rows = " + terms.size());
        return terms;
    }

    @Override
    public Term getByCursor(Cursor cursor) {

        Term term = new Term();

        int id = cursor.getColumnIndex(DatabaseContract.TermTable.COLUMN_ID);
        int course = cursor.getColumnIndex(DatabaseContract.TermTable.COLUMN_COURSE);
        int semester = cursor.getColumnIndex(DatabaseContract.TermTable.COLUMN_SEMESTER);

        term.setId(cursor.getInt(id));
        term.setCourse(cursor.getInt(course));
        term.setSemester(cursor.getInt(semester));

        return term;
    }


}
