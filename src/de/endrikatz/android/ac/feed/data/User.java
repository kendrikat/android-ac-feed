package de.endrikatz.android.ac.feed.data;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    int id = 0;
    String name = "";
    Avatar avatar = new Avatar();

    User() {

    }

    private User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        avatar = in.readParcelable(Avatar.class.getClassLoader());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeParcelable(avatar, flags);
    }

}
