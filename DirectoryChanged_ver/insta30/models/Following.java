package group30.com.instagramclone2.models;

import android.os.Parcel;

import java.text.SimpleDateFormat;

public class Following {
    private String user_id;
    private String follow_time;

    public Following(String user_id, String follow_time) {
        this.user_id = user_id;
        this.follow_time = follow_time;
    }

    public Following() {

    }

    protected Following(Parcel in){
        user_id = in.readString();
        follow_time = in.readString();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFollow_time(){
        return follow_time;
    }

    public void setFollow_time(String follow_time){
        this.follow_time = follow_time;
    }

    @Override
    public String toString() {
        return "Following{" +
                "user_id='" + user_id + '\'' +
                ", follow_time='" + follow_time +
                '}';
    }
}
