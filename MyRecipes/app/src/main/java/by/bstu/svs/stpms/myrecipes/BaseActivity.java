package by.bstu.svs.stpms.myrecipes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    boolean isNightTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        isNightTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("is_night_theme", false);
        if (isNightTheme) setTheme(R.style.DarkTheme);
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (isNightTheme != prefs.getBoolean("is_night_theme", false)) {
            this.recreate();
        }
    }
}