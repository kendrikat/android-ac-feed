package de.endrikatz.android.ac.feed.data;

import java.util.ArrayList;

public class Status {
    int id = 0;
    String message = "";
    String message_clickable = "";
    User created_by = new User();
    ArrayList<Status> replies = new ArrayList<Status>();

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

    public User getCreated_by() {
        return created_by;
    }

    public void setCreated_by(User created_by) {
        this.created_by = created_by;
    }
}
