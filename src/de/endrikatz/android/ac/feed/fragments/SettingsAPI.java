package de.endrikatz.android.ac.feed.fragments;

import android.os.Bundle;
import de.endrikatz.android.ac.feed.R;
import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import org.holoeverywhere.preference.PreferenceFragment;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.OnSharedPreferenceChangeListener;

public class SettingsAPI extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_api);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        Crouton.makeText(this.getActivity(),
                getString(R.string.global_msg_info_saved_setting),
                Style.INFO, Configuration.DURATION_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        getSupportActionBar().setSubtitle(R.string.menu_settings);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}