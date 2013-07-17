package de.endrikatz.android.ac.feed.fragments;

import de.endrikatz.android.ac.feed.R;
import org.holoeverywhere.preference.PreferenceFragment;

import android.os.Bundle;

public class SettingsAPI extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_api);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setSubtitle("Settings");
    }
}