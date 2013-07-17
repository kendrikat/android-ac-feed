package de.endrikatz.android.ac.feed.api;

import android.content.Context;
import android.net.Uri;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import de.endrikatz.android.ac.feed.R;
import de.endrikatz.android.ac.feed.data.StatusList;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.springframework.http.ResponseEntity;

public class StatusRequest extends SpringAndroidSpiceRequest<StatusList> {
    private String tag;
    private Context ctx;

    public StatusRequest(String tag, Context ctx) {
        super(StatusList.class);
        this.tag = tag;
        this.ctx = ctx;
    }

    @Override
    public StatusList loadDataFromNetwork() throws Exception {
        // TODO: cleanup
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        String apiUrl = prefs.getString(ctx.getString(R.string.const_pref_api_url), "");
        String apiToken = prefs.getString(ctx.getString(R.string.const_pref_api_token), "");

        Uri.Builder uriBuilder = Uri.parse(apiUrl).buildUpon();
        uriBuilder.appendQueryParameter(Constants.CONST_API_PARAM_TYPE_PATH, Constants.CONST_API_PARAM_TYPE_PATH_STATUS);
        uriBuilder.appendQueryParameter(Constants.CONST_API_PARAM_TYPE_FORMAT, Constants.CONST_API_PARAM_TYPE_FORMAT_JSON);
        uriBuilder.appendQueryParameter(Constants.CONST_API_STATUS_PARAM_TOKEN, apiToken);

        String url = uriBuilder.build().toString();

        ResponseEntity<StatusList> response = getRestTemplate().getForEntity(url,
                StatusList.class);
        return response.getBody();
    }

    public String createCacheKey() {
        return "feed." + tag;
    }
}
