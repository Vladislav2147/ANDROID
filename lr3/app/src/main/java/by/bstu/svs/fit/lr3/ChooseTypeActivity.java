package by.bstu.svs.fit.lr3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);
    }

    public void onClickBackButton(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle arguments = getIntent().getExtras();
        if(arguments != null) {
            intent.putExtra("firstName", arguments.getString("firstName"));
            intent.putExtra("secondName", arguments.getString("secondName"));
            intent.putExtra("age", (Integer) arguments.get("age"));
        }
        startActivity(intent);
    }
}