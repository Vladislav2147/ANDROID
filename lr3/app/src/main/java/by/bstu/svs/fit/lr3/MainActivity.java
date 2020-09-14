package by.bstu.svs.fit.lr3;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

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
            Log.d("Person", person.getAge() + " " + person.getSecondName());
        }

        manager.sortByAgeThenBySecondName(course.getListeners());

        for (Person person :
                course.getListeners()) {
            Log.d("Sorted", person.getAge() + " " + person.getSecondName());
        }


    }



}