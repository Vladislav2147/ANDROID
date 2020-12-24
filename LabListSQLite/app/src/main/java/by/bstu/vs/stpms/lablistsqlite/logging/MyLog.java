package by.bstu.vs.stpms.lablistsqlite.logging;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyLog {

    private final static String TAG = "MyLog";
    private File logFile;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public MyLog(Context context) {
        logFile = new File(context.getFilesDir(), "log.txt");
    }

    public void info(String tag, String message) {

        Log.i(tag, message);
        String currentTime = dtf.format(LocalDateTime.now());
        String toLog = currentTime + " " + tag + ": " + message;

        new FileAsyncTask().execute(toLog);
    }

    public void error(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);

        String currentTime = dtf.format(LocalDateTime.now());
        String toLog = currentTime + " " + tag + ": " + message + " " + throwable;

        new FileAsyncTask().execute(toLog);
    }

    class FileAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            try (FileWriter fw = new FileWriter(logFile, true)) {

                fw.append(strings[0]).append("\n");
                Log.i(TAG, "appendToFile success");

            } catch (IOException e) {
                Log.e(TAG, "appendToFile ", e);
            }
            return null;
        }
    }

}
