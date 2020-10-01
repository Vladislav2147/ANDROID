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
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

import by.bstu.svs.fit.lr3.manager.ImageManager;
import by.bstu.svs.fit.lr3.manager.Manager;
import by.bstu.svs.fit.lr3.person.Person;

public class RegistrationActivity extends AppCompatActivity {

    String firstName;
    String secondName;
    String age;
    String email;
    String number;
    String socialNetwork;
    String image;

    File imageDirectory;

    private static int RESULT_LOAD_IMG = 1;
    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        File json = new File(super.getFilesDir(), "json.json");
        if (!json.exists())  {
            try {
                json.createNewFile();
                Manager manager = new Manager();
                manager.writeToFile(manager.generateCourse(0), json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String imageDir = super.getFilesDir().getAbsolutePath() + "/images";
        imageDirectory = new File(imageDir);
        imageDirectory.mkdirs();

    }

    public void onClickNextButton(View view) {

        Intent intent = new Intent(this, ChooseTypeActivity.class);
        firstName = ((EditText)findViewById(R.id.firstName)).getText().toString();
        secondName = ((EditText)findViewById(R.id.secondName)).getText().toString();
        age = ((EditText)findViewById(R.id.age)).getText().toString();
        email = ((EditText)findViewById(R.id.email)).getText().toString();
        number = ((EditText)findViewById(R.id.phone)).getText().toString();
        socialNetwork = ((EditText)findViewById(R.id.social)).getText().toString();

        Integer ageInt = null;
        try {
            ageInt = Integer.parseInt(age);
        }
        catch (NumberFormatException ex) {
            Log.e(TAG, "onClickNextButton: ", ex);
        }

        Person person = new Person(firstName, secondName, ageInt, email, number, socialNetwork, image);
        intent.putExtra("person", person);
        startActivity(intent);

    }

    public void chooseImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_LOAD_IMG);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    File file = new File(picturePath);
                    if (file.exists()) {
                        image = file.getName();
                        File imageFile = new File(imageDirectory, image);
                        imageFile.createNewFile();
                        ImageManager.copy(file, imageFile);
                        Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                        ImageView myImage = (ImageView) findViewById(R.id.image);
                        myImage.setImageBitmap(myBitmap);
                    }
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, "onActivityResult: ", e);
        }


    }


}