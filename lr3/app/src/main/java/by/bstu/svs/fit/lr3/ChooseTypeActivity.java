package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import by.bstu.svs.fit.lr3.person.Person;

public class ChooseTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);
    }

    public void onClickBackButton(View view) {
        finish();
    }

    public void enableNextButton(View view) {
        ((Button)findViewById(R.id.nextButton)).setEnabled(true);
    }

    public void onClickNextButton(View view) {

        try {

            Intent intent;

            if (((RadioButton)findViewById(R.id.radioStudent)).isChecked()) {
                intent = new Intent(this, StudentActivity.class);
            }
            else if (((RadioButton)findViewById(R.id.radioEmployee)).isChecked()) {
                intent = new Intent(this, EmployeeActivity.class);
            }
            else {
                throw new Exception("Something goes wrong");
            }

            intent.putExtra("person", (Person)getIntent().getSerializableExtra("person"));
            startActivity(intent);

        } catch (Exception ex) {
            Log.e("Exception", Objects.requireNonNull(ex.getMessage()));
        }

    }
}