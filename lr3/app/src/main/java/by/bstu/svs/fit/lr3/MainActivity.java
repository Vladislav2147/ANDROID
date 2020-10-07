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

    private static final String json = "json.json";
    private RecyclerView listenersRecycleView;
    private ListenerAdapter listenerAdapter;
    private Manager manager;
    private Course course;
    Intent personIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personIntent = new Intent(this, PersonActivity.class);
        manager = new Manager();
        course = manager.getCourseFromFile(new File(super.getFilesDir(), json)).orElse(new Course());
        initRecycleView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        course = manager.getCourseFromFile(new File(super.getFilesDir(), json)).orElse(new Course());
        listenerAdapter.setItems(course.getListeners());
    }

    public void goToRegistration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void initRecycleView() {

        listenerAdapter = new ListenerAdapter(course.getListeners(), super.getFilesDir());
        listenerAdapter.setListener(person -> {
            personIntent.putExtra("person", person);
            startActivity(personIntent);
        });
        listenersRecycleView = findViewById(R.id.listeners_recycler_view);
        listenersRecycleView.setLayoutManager(new LinearLayoutManager(this));
        listenersRecycleView.setAdapter(listenerAdapter);


    }

}