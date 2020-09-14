package by.bstu.svs.fit.lr3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.manager.Manager;
import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        File file = new File(getExternalFilesDir(null),"json.json");
//
//        Log.d("isCreated: ", String.valueOf(file.createNewFile()));
//        Log.d("dir: ", getExternalFilesDir(null).getAbsolutePath());
//
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//        Log.d("file: ", bufferedReader.readLine());
        generateFile();

    }

    public void generateFile() throws IOException {

        Manager manager = new Manager();
        Course course = manager.generateCourse(25);
        File file = new File(getExternalFilesDir(null),"json.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = objectMapper.writeValueAsString(course);

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(json);
        fileWriter.close();
    }

}