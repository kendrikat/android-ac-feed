package de.endrikatz.android.ac.feed.fragments;

import android.os.Bundle;
import de.endrikatz.android.ac.feed.R;
import org.holoeverywhere.preference.SharedPreferences.OnSharedPreferenceChangeListener;

public class SettingsGeneral extends SettingsFragment implements OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_general);
    }

}