package de.endrikatz.android.ac.feed.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Avatar implements Parcelable {

    public static final Parcelable.Creator<Avatar> CREATOR
            = new Parcelable.Creator<Avatar>() {
        public Avatar createFromParcel(Parcel in) {
            return new Avatar(in);
        }

        public Avatar[] newArray(int size) {
            return new Avatar[size];
        }
    };
    private String large = "";

    Avatar() {
    }

    private Avatar(Parcel in) {
        large = in.readString();
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(large);
    }

}
