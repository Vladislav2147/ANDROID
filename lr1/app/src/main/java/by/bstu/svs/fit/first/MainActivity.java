package by.bstu.svs.fit.first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import by.bstu.svs.fit.first.shichko.TextFunction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int count = 0; count < 10; count++) {
            Log.d("MainActivity", "Counter " + count);
        }
        displayText();

    }

    private void displayText() {
        //TODO something
        TextFunction textFunction = new TextFunction();
        TextView textView = findViewById(R.id.New_Text);
        textView.setText(textFunction.getValue());
    }

}