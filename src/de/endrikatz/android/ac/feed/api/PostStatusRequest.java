package de.endrikatz.android.ac.feed.api;

import android.content.Context;
import android.net.Uri;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import de.endrikatz.android.ac.feed.R;
import de.endrikatz.android.ac.feed.data.Status;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class PostStatusRequest extends SpringAndroidSpiceRequest<Status> {
    private String apiUrl;
    private String apiToken;
    private Status status;

    public PostStatusRequest(Status status, Context ctx) {
        super(Status.class);
        this.status = status;

        // TODO: cleanup
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        apiUrl = prefs.getString(ctx.getString(R.string.const_pref_api_url), "");
        apiToken = prefs.getString(ctx.getString(R.string.const_pref_api_token), "");
    }

    @Override
    public Status loadDataFromNetwork() throws Exception {

        Uri.Builder uriBuilder = Uri.parse(apiUrl).buildUpon();
        uriBuilder.appendQueryParameter(Constants.CONST_API_PARAM_TYPE_PATH, Constants.CONST_API_PARAM_TYPE_PATH_STATUS);
        uriBuilder.appendQueryParameter(Constants.CONST_API_PARAM_TYPE_FORMAT, Constants.CONST_API_PARAM_TYPE_FORMAT_JSON);
        uriBuilder.appendQueryParameter(Constants.CONST_API_STATUS_PARAM_TOKEN, apiToken);
        // TODO: find a way to allow something like: "...?path_info=foo/bar" https://www.activecollab.com/docs/manuals/developers-version-3/api/making-a-requests (see "command")
        String url = uriBuilder.build().toString().replace(Constants.CONST_API_PARAM_TYPE_PATH_STATUS, Constants.CONST_API_PARAM_TYPE_PATH_STATUS_ADD);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add(Constants.CONST_API_PARAM_TYPE_SUBMITTED, Constants.CONST_API_PARAM_TYPE_SUBMITTED);
        params.add(Constants.CONST_API_PARAM_STATUS_UPDATE, status.getMessage());

        getRestTemplate().getMessageConverters().add(new FormHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        return getRestTemplate().postForObject(url, request, Status.class);
    }

    public String createCacheKey() {
        return "status." + String.valueOf(System.currentTimeMillis());
    }
}
