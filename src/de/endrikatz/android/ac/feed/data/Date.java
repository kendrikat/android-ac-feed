package de.endrikatz.android.ac.feed.data;

public class Date {
    String formatted_gmt = "";
    int timestamp = 0;

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
}
