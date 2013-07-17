package de.endrikatz.android.ac.feed.fragments;

import android.os.Bundle;
import de.endrikatz.android.ac.feed.R;
import org.holoeverywhere.preference.PreferenceFragment;

public class SettingsAPI extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_api);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setSubtitle(R.string.menu_settings);
    }
}