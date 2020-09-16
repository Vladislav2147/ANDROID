package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeeActivity extends AppCompatActivity {

    Bundle arguments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        arguments = getIntent().getExtras();
    }

    public void onClickBackButton(View view) {
        finish();
    }

    public void onClickNextButton(View view) {
        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra("activity", this.getClass().getName());
        intent.putExtra("firstName", arguments.getString("firstName"));
        intent.putExtra("secondName", arguments.getString("secondName"));
        intent.putExtra("age", arguments.getString("age"));
        intent.putExtra("organisation", ((TextView)findViewById(R.id.organisation)).getText().toString());
        startActivity(intent);
    }
}