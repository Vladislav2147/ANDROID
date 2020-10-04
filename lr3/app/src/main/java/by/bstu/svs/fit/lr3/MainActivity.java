package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import by.bstu.svs.fit.lr3.course.Course;
import by.bstu.svs.fit.lr3.manager.Manager;
import by.bstu.svs.fit.lr3.recycler.ListenerAdapter;

public class MainActivity extends AppCompatActivity {

    //TODO personList and unique person activity
    private static final String json = "json.json";
    private RecyclerView listenersRecycleView;
    private ListenerAdapter listenersAdepter;
    private Manager manager;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new Manager();
        course = manager.getCourseFromFile(new File(super.getFilesDir(), json)).orElse(new Course());
        initRecycleView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        course = manager.getCourseFromFile(new File(super.getFilesDir(), json)).orElse(new Course());
        listenersAdepter.setItems(course.getListeners());
    }

    public void goToRegistration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void initRecycleView() {


        listenersAdepter = new ListenerAdapter(course.getListeners(), super.getFilesDir());
        listenersRecycleView = findViewById(R.id.listeners_recycler_view);
        listenersRecycleView.setLayoutManager(new LinearLayoutManager(this));
        listenersRecycleView.setAdapter(listenersAdepter);

    }

}