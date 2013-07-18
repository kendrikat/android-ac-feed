package de.endrikatz.android.ac.feed.api;

import android.content.Context;
import android.view.ViewGroup;
import com.octo.android.robospice.request.simple.BitmapRequest;
import com.octo.android.robospice.spicelist.BitmapSpiceManager;
import com.octo.android.robospice.spicelist.SpiceArrayAdapter;
import com.octo.android.robospice.spicelist.SpiceListItemView;
import de.endrikatz.android.ac.feed.data.Status;
import de.endrikatz.android.ac.feed.data.StatusList;
import de.endrikatz.android.ac.feed.views.StatusUpdateView;

import java.io.File;

public class StatusUpdateListAdapter extends SpiceArrayAdapter<Status> {

    public StatusUpdateListAdapter(Context context,
                                   BitmapSpiceManager spiceManagerBitmap, StatusList statusList) {
        super(context, spiceManagerBitmap, statusList);
    }

    @Override
    public BitmapRequest createRequest(Status status, int imageIndex,
                                       int requestImageWidth, int requestImageHeight) {
        File tempFile = new File(getContext().getCacheDir(),
                "THUMB_USER_TEMP_" + status.getUser().getId());
        return new BitmapRequest(status.getUser().getAvatar().getLarge(),
                requestImageWidth, requestImageHeight, tempFile);
    }

    @Override
    public SpiceListItemView<Status> createView(Context context, ViewGroup parent) {
        return new StatusUpdateView(getContext());
    }
}