package de.endrikatz.android.ac.feed.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Status implements Parcelable {

    public static final Parcelable.Creator<Status> CREATOR
            = new Parcelable.Creator<Status>() {
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        public Status[] newArray(int size) {
            return new Status[size];
        }
    };
    int id = 0;
    String message = "";
    String message_clickable = "";
    User created_by = new User();
    Date created_on = new Date();
    ArrayList<Status> replies = new ArrayList<Status>();

    public Status() {
    }

    private Status(Parcel in) {
        id = in.readInt();
        message = in.readString();
        created_by = in.readParcelable(User.class.getClassLoader());
        created_on = in.readParcelable(Date.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_clickable() {
        return message_clickable;
    }

    public void setMessage_clickable(String message_clickable) {
        this.message_clickable = message_clickable;
    }

    public User getUser() {
        return created_by;
    }

    public void setUser(User user) {
        this.created_by = user;
    }

    public Date getDate() {
        return created_on;
    }

    public void setDate(Date date) {
        this.created_on = date;
    }

    public int getReplyCount() {
        return replies.size();
    }

    public ArrayList<Status> getReplies() {
        return replies;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(message);
        dest.writeParcelable(created_by, flags);
        dest.writeParcelable(created_on, flags);
    }
}
