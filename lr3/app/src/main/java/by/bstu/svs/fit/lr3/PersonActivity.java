package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import by.bstu.svs.fit.lr3.manager.ImageManager;
import by.bstu.svs.fit.lr3.manager.Manager;
import by.bstu.svs.fit.lr3.person.Employee;
import by.bstu.svs.fit.lr3.person.Person;
import by.bstu.svs.fit.lr3.person.Student;

public class PersonActivity extends AppCompatActivity {

    private TextView firstNameTextView;
    private TextView secondNameTextView;
    private TextView ageTextView;
    private TextView organisationTextView;
    private TextView universityTextView;
    private TextView markTextView;
    private TextView emailTextView;
    private TextView numberTextView;
    private TextView socialTextView;
    private ImageView imageView;

    private Person person;
    private File jsonFile;
    private static int RESULT_LOAD_IMG = 1;
    private static final String TAG = "PersonActivity";

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

        jsonFile = new File(super.getFilesDir(), "json.json");

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

    public void changeImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_LOAD_IMG);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {

                File galleryImageFile = getImageFileFromIntentData(data);

                if (galleryImageFile.exists()) {
                    String imageName = galleryImageFile.getName();
                    File internalImageDirectory =
                            new File(super.getFilesDir().getAbsolutePath() + "/images");
                    File internalImageFile = new File(internalImageDirectory, imageName);
                    internalImageFile.createNewFile();
                    ImageManager.copy(galleryImageFile, internalImageFile);
                    Bitmap myBitmap = BitmapFactory.decodeFile(internalImageFile.getAbsolutePath());
                    ImageView myImage = findViewById(R.id.imageConfirm);
                    myImage.setImageBitmap(myBitmap);

                    updateImage(imageName);
                }

            }
        }
        catch (Exception e) {
            Log.e(TAG, "onActivityResult: ", e);
        }

    }

    private void updateImage (String newImage) {
        Manager manager = new Manager();
        manager.getCourseFromFile(jsonFile).ifPresent(course -> {

            course.getListeners()
                    .stream()
                    .filter(listener -> listener.equals(person))
                    .findFirst()
                    .ifPresent(foundPerson -> foundPerson.setImage(newImage));
            manager.writeToFile(course, jsonFile);

        });
    }

    private File getImageFileFromIntentData(Intent data) {

        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            return new File(picturePath);
        }
        return null;

    }


}