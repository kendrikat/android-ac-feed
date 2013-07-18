package de.endrikatz.android.ac.feed.data;

import java.util.ArrayList;

public class Status {
    int id = 0;
    String message = "";
    String message_clickable = "";
    User created_by = new User();
    Date created_on = new Date();
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
}
