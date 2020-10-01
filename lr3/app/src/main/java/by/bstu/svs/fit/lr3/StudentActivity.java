package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;
import by.bstu.svs.fit.lr3.person.University;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Spinner mySpinner = findViewById(R.id.university);
        mySpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                University.values()));
    }

    public void onClickBackButton(View view) {
        finish();
    }

    public void onClickNextButton(View view) {

        Intent intent = new Intent(this, ConfirmActivity.class);

        Person person = (Person)getIntent().getSerializableExtra("person");

        University university = University.valueOf(((Spinner)findViewById(R.id.university))
                .getSelectedItem()
                .toString()
        );

        Integer mark = null;
        try {
            mark = Integer.parseInt(((EditText)findViewById(R.id.mark)).getText().toString());
        }
        catch (NumberFormatException ex) {
            Log.e("StudentAcrivity", "onClickNextButton: ", ex);
        }

        if (person != null) {
            Student student = new Student(person, mark, university);
            intent.putExtra("person", student);
        }

        startActivity(intent);

    }
}