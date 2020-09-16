package by.bstu.svs.fit.lr3;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import by.bstu.svs.fit.lr3.person.Employee;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;
import by.bstu.svs.fit.lr3.person.University;

public class ConfirmActivity extends AppCompatActivity {

    Bundle arguments;
    Person savingPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        TextView firstNameTextView = findViewById(R.id.firstNameTextView);
        TextView secondNameTextView = findViewById(R.id.secondNameTextView);
        TextView ageTextView = findViewById(R.id.ageTextView);
        TextView organisationTextView = findViewById(R.id.organisationTextView);
        TextView markTextView = findViewById(R.id.markTextView);
        TextView universityTextView = findViewById(R.id.universityTextView);

        arguments = getIntent().getExtras();

        if (arguments.getString("activity").equals(StudentActivity.class.getName())) {

            ((LinearLayout)organisationTextView.getParent()).setVisibility(View.GONE);

            Student student = new Student();
            student.setMark(arguments.getInt("mark"));
            student.setUniversity(University.valueOf(arguments.getString("university")));

            markTextView.setText(String.valueOf(student.getMark()));
            universityTextView.setText(student.getUniversity().name());

            savingPerson = student;

        }
        else {

            ((LinearLayout)markTextView.getParent()).setVisibility(View.GONE);
            ((LinearLayout)universityTextView.getParent()).setVisibility(View.GONE);

            Employee employee = new Employee();
            employee.setOrganisation(arguments.getString("organisation"));

            organisationTextView.setText(employee.getOrganisation());

            savingPerson = employee;

        }

        savingPerson.setFirstName(arguments.getString("firstName"));
        savingPerson.setSecondName(arguments.getString("secondName"));
        savingPerson.setAge(arguments.getInt("age"));

        firstNameTextView.setText(savingPerson.getFirstName());
        secondNameTextView.setText(savingPerson.getSecondName());
        ageTextView.setText(String.valueOf(savingPerson.getAge()));

    }

    public void onClickBackButton(View view) {
        finish();
    }
}