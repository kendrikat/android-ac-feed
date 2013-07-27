package de.endrikatz.android.ac.feed.views;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.octo.android.robospice.spicelist.SpiceListItemView;
import de.endrikatz.android.ac.feed.R;
import de.endrikatz.android.ac.feed.data.Status;


public class StatusUpdateView extends RelativeLayout implements
        SpiceListItemView<Status> {

    private TextView statusUserNameTextView;
    private TextView statusDateTextView;
    private TextView statusContentTextView;
    private TextView statusReplyCountTextView;
    private ImageView thumbImageView;
    private Status status;


    public StatusUpdateView(Context context) {
        super(context);
        inflateView(context);
    }

    private void inflateView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_cell_status, this);
        this.statusUserNameTextView = (TextView) this
                .findViewById(R.id.status_update_username);
        this.statusDateTextView = (TextView) this
                .findViewById(R.id.status_update_date);
        this.statusContentTextView = (TextView) this
                .findViewById(R.id.status_update_content);
        this.thumbImageView = (ImageView) this
                .findViewById(R.id.status_update_thumbnail);
        this.statusReplyCountTextView = (TextView) this
                .findViewById(R.id.status_update_replycount);
    }

    @Override
    public void update(Status status) {
        this.status = status;
        statusUserNameTextView.setText(status.getUser().getName());
        statusDateTextView.setText(status.getDate().getFormattedGmt());
        statusContentTextView.setText(String.valueOf(status.getMessage()));
        statusReplyCountTextView.setText(
                getResources().getQuantityString(R.plurals.replies, status.getReplyCount(), status.getReplyCount()));
    }

    @Override
    public Status getData() {
        return status;
    }

    @Override
    public ImageView getImageView(int imageIndex) {
        return thumbImageView;
    }

    @Override
    public int getImageViewCount() {
        return 1;
    }
}