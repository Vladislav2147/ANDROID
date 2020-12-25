package by.bstu.vs.stpms.lablistsqlite.ui.settings;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import by.bstu.vs.stpms.lablistsqlite.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    private NavController navController;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavHostFragment navHostFragment = (NavHostFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        Preference openLogFile = findPreference("open_log");
        openLogFile.setOnPreferenceClickListener(preference -> {
            navController.navigate(SettingsFragmentDirections.actionNavSettingsToNavLog());
            return false;
        });
        super.onViewCreated(view, savedInstanceState);
    }
}