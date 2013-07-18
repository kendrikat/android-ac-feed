package de.endrikatz.android.ac.feed;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.octo.android.robospice.GsonSpringAndroidSpiceService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.spicelist.BitmapSpiceManager;
import de.endrikatz.android.ac.feed.api.PostStatusRequest;
import de.endrikatz.android.ac.feed.api.StatusRequest;
import de.endrikatz.android.ac.feed.api.StatusUpdateListAdapter;
import de.endrikatz.android.ac.feed.data.Status;
import de.endrikatz.android.ac.feed.data.StatusList;
import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.widget.EditText;

public class ShowFeed extends Activity {

    protected static final String KEY_LAST_REQUEST_CACHE_KEY = "lastRequestCacheKey";
    private static final String TAG = "ShowFeed";
    public BitmapSpiceManager spiceManagerBinary = new BitmapSpiceManager();
    protected SpiceManager contentManager = new SpiceManager(
            GsonSpringAndroidSpiceService.class);
    protected String lastRequestCacheKey;
    private ListView statusUpdateListView;
    private StatusUpdateListAdapter statusUpdateListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);
        setSupportProgressBarIndeterminateVisibility(false);

        statusUpdateListView = (ListView) findViewById(R.id.listview_status_updates);

        if (PreferenceManager.getDefaultSharedPreferences(this).contains(getString(R.string.const_pref_api_token))) {
            performRequest(false);
        }

        if (getIntent().hasExtra(Intent.EXTRA_TEXT)) {
            String message = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            //String subject = getIntent().getStringExtra(Intent.EXTRA_SUBJECT);
            openPostStatusUpdateDialog(message);
        }
    }

    @Override
    protected void onStart() {
        contentManager.start(this);
        spiceManagerBinary.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        contentManager.shouldStop();
        spiceManagerBinary.shouldStop();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_settings:
                Intent intent = new Intent(this, Settings.class);
                startActivity(intent);
                return true;

            case R.id.menu_compose:
                openPostStatusUpdateDialog("");
                return true;

            case R.id.menu_refresh:
                performRequest(true);
                return true;

            case R.id.menu_quit:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!TextUtils.isEmpty(lastRequestCacheKey)) {
            outState.putString(KEY_LAST_REQUEST_CACHE_KEY, lastRequestCacheKey);
        }
        super.onSaveInstanceState(outState);
    }

    protected boolean checkInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null
                && savedInstanceState.containsKey(KEY_LAST_REQUEST_CACHE_KEY)) {
            lastRequestCacheKey = savedInstanceState
                    .getString(KEY_LAST_REQUEST_CACHE_KEY);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (checkInstanceState(savedInstanceState)) {

            contentManager.addListenerIfPending(StatusList.class,
                    lastRequestCacheKey, new FeedRequestListener());
            contentManager
                    .getFromCache(StatusList.class, lastRequestCacheKey,
                            DurationInMillis.ALWAYS_EXPIRED,
                            new FeedRequestListener());
        }
    }

    private void updateListViewContent(StatusList statusList) {
        statusUpdateListAdapter = new StatusUpdateListAdapter(this, spiceManagerBinary, statusList);
        statusUpdateListView.setAdapter(statusUpdateListAdapter);
        statusUpdateListView.setVisibility(View.VISIBLE);
    }

    private void performRequest(Boolean refresh) {
        this.setSupportProgressBarIndeterminateVisibility(true);

        StatusRequest request = new StatusRequest(getApplicationContext());
        lastRequestCacheKey = request.createCacheKey();

        if (refresh) {
            contentManager.removeDataFromCache(StatusList.class, lastRequestCacheKey);
        }
        contentManager.execute(request, lastRequestCacheKey,
                DurationInMillis.ONE_HOUR, new FeedRequestListener());
    }

    public void performPostStatusUpdateRequest(Status status) {
        this.setProgressBarIndeterminateVisibility(true);

        PostStatusRequest request = new PostStatusRequest(status,
                getApplicationContext());
        lastRequestCacheKey = request.createCacheKey();
        contentManager.execute(request, lastRequestCacheKey,
                DurationInMillis.ALWAYS_EXPIRED, new PostStatusRequestListener());
    }

    public void openPostStatusUpdateDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowFeed.this);
        final EditText input = new EditText(this);
        input.setText(message);
        builder.setView(input);
        builder.setMessage(R.string.form_post_update_title);
        builder.setPositiveButton(R.string.global_alert_submit,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Status status = new Status();
                        status.setMessage(input.getText().toString().trim());
                        performPostStatusUpdateRequest(status);
                    }
                });
        builder.setNegativeButton(R.string.global_alert_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private class FeedRequestListener implements RequestListener<StatusList> {
        @Override
        public void onRequestFailure(SpiceException e) {
            setSupportProgressBarIndeterminateVisibility(false);
            Crouton.makeText(ShowFeed.this,
                    getString(R.string.global_msg_error_request) + e.getMessage(),
                    Style.ALERT, Configuration.DURATION_LONG).show();
        }

        @Override
        public void onRequestSuccess(StatusList statusList) {
            Crouton.makeText(ShowFeed.this,
                    getString(R.string.global_msg_info_loaded_messages),
                    Style.INFO, Configuration.DURATION_SHORT).show();
            updateListViewContent(statusList);
            setSupportProgressBarIndeterminateVisibility(false);
        }
    }

    private class PostStatusRequestListener implements RequestListener<Status> {
        @Override
        public void onRequestFailure(SpiceException e) {
            setSupportProgressBarIndeterminateVisibility(false);
            Crouton.makeText(ShowFeed.this,
                    getString(R.string.global_msg_error_request) + e.getMessage(),
                    Style.ALERT, Configuration.DURATION_LONG).show();
        }

        @Override
        public void onRequestSuccess(Status status) {
            Crouton.makeText(ShowFeed.this,
                    getString(R.string.global_msg_info_posted_message),
                    Style.INFO, Configuration.DURATION_SHORT).show();

            setSupportProgressBarIndeterminateVisibility(false);
        }
    }
}
