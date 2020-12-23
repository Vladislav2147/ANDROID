package by.bstu.vs.stpms.lablistsqlite.model.repository.async;

import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import java.util.function.Supplier;

public class QueryAsyncTask<E> extends AsyncTask<Void, Void, E> {

    private final MutableLiveData<E> liveData;
    private final Supplier<E> doInBackground;

    public QueryAsyncTask(MutableLiveData<E> liveData, Supplier<E> doInBackground) {
        this.liveData = liveData;
        this.doInBackground = doInBackground;
    }

    @SafeVarargs
    @Override
    protected final E doInBackground(Void... items) {
        return doInBackground.get();
    }

    @Override
    protected void onPostExecute(E e) {
        super.onPostExecute(e);
        liveData.setValue(e);
    }
}