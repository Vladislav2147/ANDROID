package by.bstu.vs.stpms.lablistsqlite.ui.settings;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import by.bstu.vs.stpms.lablistsqlite.R;
import by.bstu.vs.stpms.lablistsqlite.logging.FileLog;


public class LogFragment extends Fragment {

    private TextView logTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logTextView = view.findViewById(R.id.log_info);
        new LogAsyncTask().execute();
    }

    class LogAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            return FileLog.getInstance().readLog();
        }

        @Override
        protected void onPostExecute(String s) {
            logTextView.setText(s);
        }
    }
}