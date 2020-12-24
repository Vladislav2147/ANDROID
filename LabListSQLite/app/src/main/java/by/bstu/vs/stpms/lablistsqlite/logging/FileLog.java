package by.bstu.vs.stpms.lablistsqlite.logging;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLog {

    private final static String TAG = "MyLog";
    private final static long MAX_BYTE_SIZE = 4 * 1024 * 1024;
    private File logFile;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
    private static FileLog instance;

    public FileLog(Context context) {
        logFile = new File(context.getFilesDir(), "log.txt");
        if (logFile.length() > MAX_BYTE_SIZE) {
            try {
                logFile.delete();
                logFile.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "ctor ", e);
            }
        }
    }

    public static FileLog getInstance(Context context) {
        if (instance == null) {
            instance = new FileLog(context);
        }
        return instance;
    }

    public static FileLog getInstance() {
        return instance;
    }

    public void info(String tag, String message) {

        Log.i(tag, message);
        String currentTime = dtf.format(LocalDateTime.now());
        String toLog = "INFO " + currentTime + " " + tag + ": " + message;
        append(toLog);

    }

    public void error(String tag, String message, Throwable e) {

        Log.e(tag, "Error: " + message, e);
        String currentTime = dtf.format(LocalDateTime.now());
        String toLog = "ERROR " + currentTime + " " + tag + " " + message + "; " + e;
        append(toLog);

    }

    private void append(String toLog) {
        try (FileWriter fw = new FileWriter(logFile, true)) {

            fw.append(toLog).append("\n");
            Log.i(TAG, "appendToFile success");

        } catch (IOException e) {
            Log.e(TAG, "appendToFile ", e);
        }
    }
}
