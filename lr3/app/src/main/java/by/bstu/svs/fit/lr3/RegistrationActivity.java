package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import by.bstu.svs.fit.lr3.manager.ImageManager;
import by.bstu.svs.fit.lr3.manager.Manager;
import by.bstu.svs.fit.lr3.person.Person;

public class RegistrationActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private EditText firstNameEditText;
    private EditText secondNameEditText;
    private EditText ageEditText;
    private EditText phoneNumberEditText;
    private EditText emailEditText;
    private EditText socialEditText;

    private String image;

    File imageDirectory;

    private static int RESULT_LOAD_IMG = 1;
    private static final String TAG = "RegistrationActivity";
    private static final int VALID_PHONE_LENGTH = 19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        linearLayout = findViewById(R.id.linear_layout);
        firstNameEditText = findViewById(R.id.firstName);
        secondNameEditText = findViewById(R.id.secondName);
        ageEditText = findViewById(R.id.age);
        emailEditText = findViewById(R.id.email);
        phoneNumberEditText = findViewById(R.id.phone);
        socialEditText = findViewById(R.id.social);

        firstNameEditText.addTextChangedListener(new RegistrationTextWatcher(firstNameEditText));
        firstNameEditText.addTextChangedListener(new RegistrationTextWatcher(secondNameEditText));
        firstNameEditText.addTextChangedListener(new RegistrationTextWatcher(ageEditText));
        firstNameEditText.addTextChangedListener(new RegistrationTextWatcher(emailEditText));
        firstNameEditText.addTextChangedListener(new RegistrationTextWatcher(phoneNumberEditText));
        firstNameEditText.addTextChangedListener(new RegistrationTextWatcher(socialEditText));

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

        List<EditText> invalidEditTextList =
                getEditTextList()
                .stream()
                .filter(editText -> editText.getError() != null)
                .collect(Collectors.toList());

        if (invalidEditTextList.size() > 0) {

            for (EditText editText: invalidEditTextList) {
                    Toast.makeText(this, editText.getError(), Toast.LENGTH_SHORT).show();
            }

        } else {
            Intent intent = new Intent(this, ChooseTypeActivity.class);
            String firstName = firstNameEditText.getText().toString();
            String secondName = secondNameEditText.getText().toString();
            String age = ageEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String number = phoneNumberEditText.getText().toString();
            String socialNetwork = socialEditText.getText().toString();

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

    private List<EditText> getEditTextList() {
        List<EditText> editTextList = new ArrayList<>();

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof EditText) {
                editTextList.add((EditText) linearLayout.getChildAt(i));
            }
        }

        return editTextList;
    }

    private class RegistrationTextWatcher implements TextWatcher {

        private EditText editText;

        public RegistrationTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            switch (editText.getId()) {
                case R.id.firstName:
                case R.id.secondName:
                    if (!isNameValid(editable.toString()))
                        editText.setError("name should be between 3 and 20");
                    else
                        editText.setError(null);
                    break;
                case R.id.age:
                    if (!isAgeValid(editable.toString()))
                        editText.setError("age should be between 18 and 100");
                    else
                        editText.setError(null);
                    break;
                case R.id.email:
                    if (!isEmailValid(editable.toString()))
                        editText.setError("invalid email");
                    else
                        editText.setError(null);
                    break;
                case R.id.phone:
                    if (!isPhoneValid(editable.toString()))
                        editText.setError("invalid phone");
                    else
                        editText.setError(null);
                    break;
                case R.id.social:
                    if (!isLinkValid(editable.toString()))
                        editText.setError("invalid link");
                    else
                        editText.setError(null);
                    break;
                default:
                    break;
            }

        }

        public boolean isNameValid(String name) {
            return name.length() >= 3 && name.length() < 20;
        }
        public boolean isAgeValid(String ageText) {
            if (ageText.length() == 0) return true;
            int age = Integer.parseInt(ageText);
            return age >= 18 && age < 100;
        }
        private boolean isEmailValid(String email) {
            String regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
            Pattern pattern = Pattern.compile(regexp);
            return pattern.matcher(email).matches();
        }
        private boolean isPhoneValid(String phone) {
            return phone.length() == 0 || phone.length() == 19;
        }
        private boolean isLinkValid(String link) {
            if (link.length() == 0) return true;
            String regexp = "^[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)$";
            Pattern pattern = Pattern.compile(regexp);
            return pattern.matcher(link).matches();
        }
    }


}