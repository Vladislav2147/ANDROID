package by.bstu.svs.stpms.myrecipes.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public class ImageManager {

    private static final String TAG = "ImageManager";

    public static void copy (File fromFile, File toFile) {
        try (InputStream in = new BufferedInputStream(new FileInputStream(fromFile));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(toFile))) {

            byte[] buffer = new byte[4096];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (IOException e) {
            Log.e(TAG, "copy: ", e);
        }
    }

    public static Optional<Bitmap> getBitMapFromFile(File file) {

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            return Optional.of(bitmap);
        } catch (Exception e) {
            Log.e(TAG, "getBitMapFromFile: ", e);
        }
        return Optional.empty();

    }

}
