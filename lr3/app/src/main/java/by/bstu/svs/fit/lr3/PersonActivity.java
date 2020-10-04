package by.bstu.svs.fit.lr3;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import by.bstu.svs.fit.lr3.manager.ImageManager;
import by.bstu.svs.fit.lr3.person.Employee;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;

public class PersonActivity extends AppCompatActivity {

    TextView firstNameTextView;
    TextView secondNameTextView;
    TextView ageTextView;
    TextView organisationTextView;
    TextView universityTextView;
    TextView markTextView;
    TextView emailTextView;
    TextView numberTextView;
    TextView socialTextView;
    ImageView imageView;

    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        person = (Person) getIntent().getSerializableExtra("person");

        firstNameTextView = findViewById(R.id.firstNameTextView);
        secondNameTextView = findViewById(R.id.secondNameTextView);
        ageTextView = findViewById(R.id.ageTextView);
        organisationTextView = findViewById(R.id.organisationTextView);
        universityTextView = findViewById(R.id.universityTextView);
        markTextView = findViewById(R.id.markTextView);
        emailTextView = findViewById(R.id.emailTextView);
        numberTextView = findViewById(R.id.numberTextView);
        socialTextView = findViewById(R.id.socialTextView);
        imageView = findViewById(R.id.imageConfirm);

        showPerson(person);

    }

    private void showPerson(Person person) {

        if (person.getImage() != null) {
            File file = new File(super.getFilesDir().getAbsolutePath() + "/images", person.getImage());
            ImageManager.getBitMapFromFile(file).ifPresent(imageView::setImageBitmap);
        }

        firstNameTextView.setText(person.getFirstName());
        secondNameTextView.setText(person.getSecondName());
        ageTextView.setText(String.valueOf(person.getAge())
                .replace("null", ""));
        emailTextView.setText(person.getEmail());
        numberTextView.setText(person.getNumber());
        socialTextView.setText(person.getSocialNetwork());



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