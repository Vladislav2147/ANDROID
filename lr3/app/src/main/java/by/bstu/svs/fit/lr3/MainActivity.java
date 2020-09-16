package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String firstName;
    String secondName;
    String age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickNextButton(View view) {

        Intent intent = new Intent(this, ChooseTypeActivity.class);
        firstName = ((EditText)findViewById(R.id.firstName)).getText().toString();
        secondName = ((EditText)findViewById(R.id.secondName)).getText().toString();
        age = ((EditText)findViewById(R.id.age)).getText().toString();


        intent.putExtra("firstName", firstName);
        intent.putExtra("secondName", secondName);
        intent.putExtra("age", age);

        startActivity(intent);

    }



}