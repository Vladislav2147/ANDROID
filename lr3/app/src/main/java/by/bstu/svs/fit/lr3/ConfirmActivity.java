package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Optional;

import by.bstu.svs.fit.lr3.person.Employee;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;
import by.bstu.svs.fit.lr3.person.University;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        getPersonFromIntent(getIntent()).ifPresent(this::showPerson);

    }

    public void onClickBackButton(View view) {
        finish();
    }

    private Optional<Person> getPersonFromIntent(Intent intent) {

        Person savingPerson;

        Bundle arguments = intent.getExtras();

        if (arguments.getString("activity").equals(StudentActivity.class.getName())) {

            Student student = new Student();
            student.setUniversity(University.valueOf(arguments.getString("university")));

            try {
                Integer mark = Integer.parseInt(arguments.getString("mark"));
                student.setMark(mark);
            }
            catch (NumberFormatException ex) {
                //TODO log
            }

            savingPerson = student;

        }
        else if (arguments.getString("activity").equals(EmployeeActivity.class.getName())){

            Employee employee = new Employee();
            employee.setOrganisation(arguments.getString("organisation"));
            savingPerson = employee;

        }
        else {
            return Optional.empty();
        }

        savingPerson.setFirstName(arguments.getString("firstName"));
        savingPerson.setSecondName(arguments.getString("secondName"));

        try {
            Integer age = Integer.parseInt(arguments.getString("age"));
            savingPerson.setAge(age);
        }
        catch (NumberFormatException ex) {
            //TODO log
        }

        return Optional.of(savingPerson);

    }

    private void showPerson(Person person) {

        TextView firstNameTextView = findViewById(R.id.firstNameTextView);
        TextView secondNameTextView = findViewById(R.id.secondNameTextView);
        TextView ageTextView = findViewById(R.id.ageTextView);
        TextView organisationTextView = findViewById(R.id.organisationTextView);
        TextView markTextView = findViewById(R.id.markTextView);
        TextView universityTextView = findViewById(R.id.universityTextView);


        firstNameTextView.setText(person.getFirstName());
        secondNameTextView.setText(person.getSecondName());
        ageTextView.setText(String.valueOf(person.getAge())
                .replace("null", ""));

        if (person instanceof Student) {

            Student student = (Student) person;

            markTextView.setText(String.valueOf(student.getMark())
                    .replace("null", ""));
            universityTextView.setText(student.getUniversity().name());

            ((LinearLayout)organisationTextView.getParent()).setVisibility(View.GONE);

        }
        else if (person instanceof Employee) {

            Employee employee = (Employee) person;

            organisationTextView.setText(employee.getOrganisation());

            ((LinearLayout)markTextView.getParent()).setVisibility(View.GONE);
            ((LinearLayout)universityTextView.getParent()).setVisibility(View.GONE);

        }






    }
}