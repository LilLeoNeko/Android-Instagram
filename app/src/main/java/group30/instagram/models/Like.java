package group30.instagram.models;
import android.os.Parcel;

public class Like {
    private String user_id;
    private String like_time;

    public Like(String user_id, String like_time) {
        this.user_id = user_id;
        this.like_time = like_time;
    }
    public Like() {

    }
    protected Like(Parcel in){
        user_id = in.readString();
        like_time = in.readString();
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getLike_time(){
        return like_time;
    }
    public void setLike_time(String like_time){
        this.like_time =like_time;
    }

    @Override
    public String toString() {
        return "Like{" +
                "user_id='" + user_id + '\'' +
                ", like_time='" + like_time +'\'' +
                '}';
    }
}
