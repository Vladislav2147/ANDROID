package by.bstu.svs.stpms.myrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Switch;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(1).setChecked(true);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.recipe_nav:
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.setting_nav:
                    break;
            }
            return true;
        });

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Switch viewSwitch = findViewById(R.id.switch1);
        boolean isNightTheme = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("is_night_theme", false);
        viewSwitch.setChecked(isNightTheme);

        viewSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            PreferenceManager
                    .getDefaultSharedPreferences(SettingsActivity.this)
                    .edit()
                    .putBoolean("is_night_theme", b)
                    .apply();
            recreate();
        });
    }
}