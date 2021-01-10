package by.bstu.vs.stpms.test.ui;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import by.bstu.vs.stpms.test.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}