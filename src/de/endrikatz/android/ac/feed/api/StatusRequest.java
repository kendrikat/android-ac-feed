package de.endrikatz.android.ac.feed.api;

import android.content.Context;
import android.net.Uri;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import de.endrikatz.android.ac.feed.R;
import de.endrikatz.android.ac.feed.data.Status;
import de.endrikatz.android.ac.feed.data.StatusList;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StatusRequest extends SpringAndroidSpiceRequest<StatusList> {
    private String apiUrl;
    private String apiToken;

    public StatusRequest(Context ctx) {
        super(StatusList.class);

        // TODO: cleanup
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        apiUrl = prefs.getString(ctx.getString(R.string.const_pref_api_url), "");
        apiToken = prefs.getString(ctx.getString(R.string.const_pref_api_token), "");
    }

    @Override
    public StatusList loadDataFromNetwork() throws Exception {

        Uri.Builder uriBuilder = Uri.parse(apiUrl).buildUpon();
        uriBuilder.appendQueryParameter(Constants.CONST_API_PARAM_TYPE_PATH, Constants.CONST_API_PARAM_TYPE_PATH_STATUS);
        uriBuilder.appendQueryParameter(Constants.CONST_API_PARAM_TYPE_FORMAT, Constants.CONST_API_PARAM_TYPE_FORMAT_JSON);
        uriBuilder.appendQueryParameter(Constants.CONST_API_STATUS_PARAM_TOKEN, apiToken);

        String url = uriBuilder.build().toString();

        ResponseEntity<StatusList> response = getRestTemplate().getForEntity(url,
                StatusList.class);

        StatusList result = response.getBody();

        return result;
    }

    public String createCacheKey() {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(apiToken.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, hash));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // TODO: cleanup
        return String.valueOf(System.currentTimeMillis());
    }
}
