package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import by.bstu.svs.fit.lr3.person.Employee;
import by.bstu.svs.fit.lr3.person.Person;

public class EmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

    }

    public void onClickBackButton(View view) {
        finish();
    }

    public void onClickNextButton(View view) {

        Intent intent = new Intent(this, ConfirmActivity.class);

        Person person = (Person)getIntent().getSerializableExtra("person");

        String organisation = ((TextView)findViewById(R.id.organisation)).getText().toString();

        if (person != null) {
            Employee employee = new Employee(person, organisation);
            intent.putExtra("person", employee);
        }

        startActivity(intent);
    }
}