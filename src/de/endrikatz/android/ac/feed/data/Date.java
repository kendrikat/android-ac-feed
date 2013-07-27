package de.endrikatz.android.ac.feed.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Date implements Parcelable {

    public static final Parcelable.Creator<Date> CREATOR
            = new Parcelable.Creator<Date>() {
        public Date createFromParcel(Parcel in) {
            return new Date(in);
        }

        public Date[] newArray(int size) {
            return new Date[size];
        }
    };
    String formatted_gmt = "";
    int timestamp = 0;

    Date() {
    }

    private Date(Parcel in) {
        in.readString();
        in.readInt();
    }

    public String getFormattedGmt() {
        return formatted_gmt;
    }

    public void setFormatted_gmt(String formatted_gmt) {
        this.formatted_gmt = formatted_gmt;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(formatted_gmt);
        dest.writeInt(timestamp);
    }

}
