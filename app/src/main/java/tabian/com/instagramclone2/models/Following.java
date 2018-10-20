package tabian.com.instagramclone2.models;

import android.os.Parcel;

import java.text.SimpleDateFormat;

public class Following {
    private String user_id;
    private String user_name;
    private String follow_id;
    private String follow_time;

    public Following(String user_id, String user_name, String follow_id, String follow_time) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.follow_id = follow_id;
        this.follow_time = follow_time;
    }

    public Following() {

    }

    protected Following(Parcel in){
        user_id = in.readString();
        user_name = in.readString();
        follow_id = in.readString();
        follow_time = in.readString();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(String follow_id) {
        this.follow_id = follow_id;
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
                ", user_name='" + user_name + '\'' +
                ", follow_id='" + follow_id + '\'' +
                ", follow_time='" + follow_time + '\'' +
                '}';
    }
}
