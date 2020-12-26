package by.bstu.vs.stpms.lablistsqlite.logging;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class FileLog {

    private static FileLog instance;

    private final static String TAG = "MyLog";
    private final static long MAX_BYTE_SIZE = 4 * 1024 * 1024; // 4 Mb
    private final static int MAX_READ_LINES = 999;
    private final File logFile;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSSxxx");

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
        String currentTime = ZonedDateTime.now().format(dtf);
        String toLog = "INFO\t" + currentTime + " " + tag + ": " + message;
        append(toLog);

    }

    public void error(String tag, String message, Throwable e) {

        Log.e(tag, "Error: " + message, e);
        String currentTime = ZonedDateTime.now().format(dtf);
        String toLog = "ERROR\t" + currentTime + " " + tag + " " + message + "; " + e;
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

    public String readLog() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(logFile.toPath(), StandardCharsets.UTF_8);
            Collections.reverse(lines);
            lines = lines.subList(0, MAX_READ_LINES < lines.size() ? (int) MAX_BYTE_SIZE : lines.size());
            for(String line: lines) {
                stringBuilder.append(line).append("\n");
            }
            Log.i(TAG, "readLog success");
        } catch (IOException e) {
            Log.e(TAG, "readLog ", e);
        }
        return stringBuilder.toString();
    }
}
