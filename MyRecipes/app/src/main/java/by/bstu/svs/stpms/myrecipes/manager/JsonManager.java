package by.bstu.svs.stpms.myrecipes.manager;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

import by.bstu.svs.stpms.myrecipes.model.CookingBook;

public class JsonManager {

    private final File jsonFile;
    private final static String TAG = "JsonManager";

    public JsonManager(File jsonFile) {
        if (!jsonFile.exists()) {
            try {
                //noinspection ResultOfMethodCallIgnored
                jsonFile.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "ctor: " + e.getMessage());
            }
        }

        this.jsonFile = jsonFile;
    }

    public void writeToFile(CookingBook book) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (FileWriter fileWriter = new FileWriter(jsonFile)) {

            String serialized = objectMapper.writeValueAsString(book);
            fileWriter.write(serialized);

        } catch (IOException e) {
            Log.e(TAG, "writeToFile: " + e.getMessage());
        }
    }

    public Optional<CookingBook> getFromFile() {

        ObjectMapper objectMapper = new ObjectMapper();
        CookingBook book;
        try {
            book = objectMapper.readValue(jsonFile, CookingBook.class);
        } catch (IOException e) {
            Log.e(TAG, "getFromFile: " + e.getMessage());
            return Optional.empty();
        }
        return Optional.ofNullable(book);

    }
}
