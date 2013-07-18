package de.endrikatz.android.ac.feed;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import de.endrikatz.android.ac.feed.api.StatusRequest;
import de.endrikatz.android.ac.feed.api.StatusUpdateListAdapter;
import de.endrikatz.android.ac.feed.data.StatusList;
import org.holoeverywhere.app.Activity;
import android.widget.ListView;
import org.holoeverywhere.widget.Toast;

import java.util.Calendar;

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

            case R.id.menu_refresh:
                performRequest();
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

    private void performRequest() {
        this.setSupportProgressBarIndeterminateVisibility(true);

        StatusRequest request = new StatusRequest(String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)), getApplicationContext());
        lastRequestCacheKey = request.createCacheKey();

        contentManager.execute(request, lastRequestCacheKey,
                DurationInMillis.ALWAYS_EXPIRED, new FeedRequestListener());
    }

    private class FeedRequestListener implements RequestListener<StatusList> {
        @Override
        public void onRequestFailure(SpiceException e) {
            setSupportProgressBarIndeterminateVisibility(false);
            Toast.makeText(getApplicationContext(),
                    "Error during request: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess(StatusList statusList) {
            updateListViewContent(statusList);
            setSupportProgressBarIndeterminateVisibility(false);
        }
    }
}
