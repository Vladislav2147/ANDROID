package by.bstu.vs.stpms.lablist.model.repository;

import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DBAsyncTask<D, E> extends AsyncTask<E, Void, SQLiteException> {

    private D dao;
    private Consumer<SQLiteException> onError;
    private BiConsumer<D, E> doInBackground;

    public DBAsyncTask(D dao, Consumer<SQLiteException> onError, BiConsumer<D, E> doInBackground) {
        this.dao = dao;
        this.onError = onError;
        this.doInBackground = doInBackground;
    }

    @SafeVarargs
    @Override
    protected final SQLiteException doInBackground(E... items) {
        try {
            doInBackground.accept(dao, items[0]);
            return null;
        } catch (SQLiteException e) {
            return e;
        }
    }

    @Override
    protected void onPostExecute(SQLiteException e) {
        super.onPostExecute(e);
        if (e != null) {
            onError.accept(e);
        }
    }
}