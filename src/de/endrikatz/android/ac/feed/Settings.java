package de.endrikatz.android.ac.feed;

import org.holoeverywhere.preference.PreferenceActivity;

import java.util.List;

public class Settings extends PreferenceActivity {

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.settings_header, target);
    }
}