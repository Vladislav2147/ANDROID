package by.bstu.svs.stpms.myrecipes;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import by.bstu.svs.stpms.myrecipes.manager.ImageManager;
import by.bstu.svs.stpms.myrecipes.manager.JsonManager;
import by.bstu.svs.stpms.myrecipes.model.Category;
import by.bstu.svs.stpms.myrecipes.model.CookingBook;
import by.bstu.svs.stpms.myrecipes.model.Recipe;
import by.bstu.svs.stpms.myrecipes.model.Time;

public class RecipeCreateActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText ingredientsEditText;
    private EditText stepsEditText;
    private Spinner categorySpinner;
    private TimePicker timePicker;
    private ImageView imageView;

    private String image;

    File imageDirectory;

    private static int RESULT_LOAD_IMG = 1;
    private static final String TAG = "RecipeCreateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create);

        titleEditText = findViewById(R.id.title);
        ingredientsEditText = findViewById(R.id.ingredients);
        stepsEditText = findViewById(R.id.steps);
        categorySpinner = findViewById(R.id.category);
        timePicker = findViewById(R.id.time_to_cook);
        imageView = findViewById(R.id.image);

        String imageDir = super.getFilesDir().getAbsolutePath() + "/images";
        imageDirectory = new File(imageDir);
        imageDirectory.mkdirs();

        titleEditText.setError("Required");
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (titleEditText.getText().length() != 0) {
                    titleEditText.setError(null);
                } else {
                    titleEditText.setError("Required");
                }
            }
        });

        categorySpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Category.values()));
        categorySpinner.setSelection(Category.OTHER.ordinal());

        timePicker.setIs24HourView(true);
        timePicker.setHour(0);
        timePicker.setMinute(0);
    }

    public void chooseImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_LOAD_IMG);

    }

    public void saveRecipe(View view) {

        if (titleEditText.getError() != null) {
            Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Recipe recipe = new Recipe();
            recipe.setTitle(titleEditText.getText().toString());
            recipe.setCategory((Category)categorySpinner.getSelectedItem());
            recipe.setIngredients(ingredientsEditText.getText().toString());
            recipe.setSteps(stepsEditText.getText().toString());

            Time timeToCook = new Time(timePicker.getHour(), timePicker.getMinute());
            recipe.setTimeToCook(timeToCook);
            recipe.setPicture(image);

            File json = new File(super.getFilesDir(), "cooking_book.json");
            JsonManager manager = new JsonManager(json);

            CookingBook book = manager.getFromFile().orElse(new CookingBook());
            book.addWithoutId(recipe);
            manager.writeToFile(book);
            Toast.makeText(this, "Recipe saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Log.e(TAG, "saveRecipe: ", e);
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

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
                        imageView.setImageBitmap(myBitmap);
                    }
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, "onActivityResult: ", e);
        }

    }
}