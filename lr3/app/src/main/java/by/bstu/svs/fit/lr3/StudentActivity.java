package by.bstu.svs.fit.lr3;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import by.bstu.svs.fit.lr3.person.University;

public class StudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Spinner mySpinner = (Spinner) findViewById(R.id.university);
        mySpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                University.values()));
    }

    public void onClickBackButton(View view) {
        finish();
    }
}