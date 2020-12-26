package by.bstu.vs.stpms.lablistsqlite;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import by.bstu.vs.stpms.lablistsqlite.logging.FileLog;
import by.bstu.vs.stpms.lablistsqlite.ui.settings.Picture;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;

    private AppBarConfiguration mAppBarConfiguration;
    private SharedPreferences.OnSharedPreferenceChangeListener listener = (sharedPreferences, s) -> recreate();
    private boolean isNightMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
        applyTheme(sharedPreferences);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

        FileLog.getInstance(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_term, R.id.nav_subject, R.id.nav_lab, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            List<String> addableFragments = new ArrayList<>();
            addableFragments.add(getString(R.string.menu_term));
            addableFragments.add(getString(R.string.menu_subject));
            addableFragments.add(getString(R.string.menu_lab));

            String currentFragment = navController.getCurrentDestination().getLabel().toString();
            if (addableFragments.stream().noneMatch(currentFragment::equals)) fab.setVisibility(View.GONE);
            else fab.setVisibility(View.VISIBLE);

        });

        customizeHeader(sharedPreferences);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void applyTheme(SharedPreferences prefs) {
        isNightMode = prefs.getBoolean("night_mode", false);
        if(isNightMode) {
            setTheme(R.style.Theme_DarkTheme_NoActionBar);
        } else {
            setTheme(R.style.Theme_LabList_NoActionBar);
        }
    }

    private void customizeHeader(SharedPreferences prefs) {
        Integer pictureId = Integer.parseInt(prefs.getString("header_image", "1"));

        ConstraintLayout navHeader = navigationView.getHeaderView(0).findViewById(R.id.nav_header_main);
        Picture.getById(pictureId).ifPresent(picture -> {
            switch(picture) {
                case PROGRAMMING:
                    navHeader.setBackgroundResource(R.drawable.programming);
                    break;
                case PHYSICS:
                    navHeader.setBackgroundResource(R.drawable.physics);
                    break;
                case CHEMISTRY:
                    navHeader.setBackgroundResource(R.drawable.chemistry);
                    break;
            }
        });
    }

}