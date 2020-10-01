package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Optional;

import by.bstu.svs.fit.lr3.manager.ImageManager;
import by.bstu.svs.fit.lr3.manager.Manager;
import by.bstu.svs.fit.lr3.person.Employee;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;

public class ConfirmActivity extends AppCompatActivity {

    Manager manager = new Manager();
    Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        person = Optional.ofNullable((Person)getIntent().getSerializableExtra("person")).orElse(new Person());
        showPerson(person);

    }

    public void onClickBackButton(View view) {
        Log.i("ConfirmActivity", "onClickBackButton");
        finish();
    }

    public void onClickSaveButton(View view) {
        Log.i("ConfirmActivity", "onClickSaveButton");
        File file = new File(super.getFilesDir(), "json.json");
        manager.addPersonToCourseInFile(file, person);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }



    private void showPerson(Person person) {

        TextView firstNameTextView = findViewById(R.id.firstNameTextView);
        TextView secondNameTextView = findViewById(R.id.secondNameTextView);
        TextView ageTextView = findViewById(R.id.ageTextView);
        TextView organisationTextView = findViewById(R.id.organisationTextView);
        TextView markTextView = findViewById(R.id.markTextView);
        TextView universityTextView = findViewById(R.id.universityTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView phoneTextView = findViewById(R.id.numberTextView);
        TextView socialTextView = findViewById(R.id.socialTextView);
        ImageView image = findViewById(R.id.imageConfirm);

        if (person.getImage() != null) {
            File file = new File(super.getFilesDir().getAbsolutePath() + "/images", person.getImage());
            ImageManager.getBitMapFromFile(file).ifPresent(image::setImageBitmap);
        }

        firstNameTextView.setText(person.getFirstName());
        secondNameTextView.setText(person.getSecondName());
        ageTextView.setText(String.valueOf(person.getAge())
                .replace("null", ""));
        emailTextView.setText(person.getEmail());
        phoneTextView.setText(person.getNumber());
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