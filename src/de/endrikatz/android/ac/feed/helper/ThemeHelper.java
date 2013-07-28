package de.endrikatz.android.ac.feed.helper;

import android.content.Context;
import de.endrikatz.android.ac.feed.R;
import org.holoeverywhere.preference.PreferenceManager;

public class ThemeHelper {
    public static void checkThemePreference(Context ctx) {

        // integer-array as index not supported
        int themePreference = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(ctx)
                .getString(ctx.getString(R.string.const_pref_theme_select), "0"));

        if (themePreference == 0) {
            ctx.setTheme(R.style.Holo_Theme);
        } else {
            ctx.setTheme(R.style.Holo_Theme_Light);
        }
    }
}
