package by.bstu.vs.stpms.lablistsqlite.ui.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import by.bstu.vs.stpms.lablistsqlite.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}