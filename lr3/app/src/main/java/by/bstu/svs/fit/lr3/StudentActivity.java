package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import by.bstu.svs.fit.lr3.person.Person;
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

        String university = ((Spinner)findViewById(R.id.university)).getSelectedItem().toString();

        intent.putExtra("mark", ((EditText)findViewById(R.id.mark)).getText().toString());
        intent.putExtra("university", university);

        //Student student = new Student(person, );

        startActivity(intent);

    }
}