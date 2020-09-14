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
import by.bstu.svs.fit.lr3.person.Person;
import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {

    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(getExternalFilesDir(null),"json.json");

        Manager manager = new Manager();
        Course course = manager.generateCourse(file);
        for (Person person :
                course.getListeners()) {
            Log.d("Person", person.toString());
        }
    }

//    public void generateFile() throws IOException {
//
//        Manager manager = new Manager();
//        Course course = manager.generateCourse(25);
//        File file = new File(getExternalFilesDir(null),"json.json");
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        String json = objectMapper.writeValueAsString(course);
//
//        FileWriter fileWriter = new FileWriter(file);
//        fileWriter.write(json);
//        fileWriter.close();
//    }

}