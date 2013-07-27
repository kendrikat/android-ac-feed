package de.endrikatz.android.ac.feed;

import android.os.Bundle;
import com.actionbarsherlock.view.Menu;
import de.endrikatz.android.ac.feed.data.Status;

import java.util.ArrayList;

public class ShowReplies extends ShowFeed {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra(KEY_REPLIES)) {
            ArrayList<Status> replies = getIntent().getParcelableArrayListExtra(KEY_REPLIES);
            super.updateListViewContent(replies);
        }
    }

    @Override
    protected void updateFeed() {
        // Do not update
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Do not show menu
        return false; //return super.onCreateOptionsMenu(menu);
    }
}
