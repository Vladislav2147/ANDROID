package by.bstu.vs.stpms.test;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

        appendToFile(toLog);
    }

    public void error(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);

        String currentTime = dtf.format(LocalDateTime.now());
        String toLog = currentTime + " " + tag + ": " + message + " " + throwable;

        appendToFile(toLog);
    }

    public void appendToFile(String line) {
        try (FileWriter fw = new FileWriter(logFile, true)) {

            fw.append(line).append("\n");

        } catch (IOException e) {
            Log.e(TAG, "appendToFile ", e);
        }
    }

}
