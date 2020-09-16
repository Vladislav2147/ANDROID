package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

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

        if (((RadioButton)findViewById(R.id.radioStudent)).isChecked()) {
            Intent intent = new Intent(this, StudentActivity.class);
            startActivity(intent);
        }
        else if (((RadioButton)findViewById(R.id.radioEmployee)).isChecked()) {
            Intent intent = new Intent(this, EmployeeActivity.class);
            startActivity(intent);
        }

    }
}