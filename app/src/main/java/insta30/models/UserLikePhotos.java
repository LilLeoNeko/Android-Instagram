package tabian.com.instagramclone2.models;

import android.os.Parcel;

public class UserLikePhotos {
    private String user_who_like;
    private String user_own_pic;
    private String img_id;
    private String img_URL;
    private String like_time;

    public UserLikePhotos(String user_who_like, String user_own_pic, String img_id, String img_URL, String like_time){
        this.user_who_like = user_who_like;
        this.user_own_pic = user_own_pic;
        this.img_id = img_id;
        this.img_URL = img_URL;
        this.like_time = like_time;
    }
    public UserLikePhotos(){

    }
    protected UserLikePhotos(Parcel in){
        user_who_like = in.readString();
        user_own_pic = in.readString();
        img_id = in.readString();
        img_URL = in.readString();
        like_time = in.readString();
    }

    public String getUser_who_like(){
        return user_who_like;
    }

    public void setUser_who_like(String user_who_like) {
        this.user_who_like = user_who_like;
    }

    public String getUser_own_pic(){
        return user_own_pic;
    }

    public void setUser_own_pic(String user_own_pic) {
        this.user_own_pic = user_own_pic;
    }

    public String getImg_id(){
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public String getImg_URL(){
        return img_URL;
    }

    public void setImg_URL(String img_URL) {
        this.img_URL = img_URL;
    }

    public String getLike_time(){
        return like_time;
    }

    public void setLike_time(String like_time) {
        this.like_time = like_time;
    }

    @Override
    public String toString() {
        return "UserLikePhotos{" +
                "user_who_like='" + user_who_like + '\'' +
                ", user_own_pic='" + user_own_pic +'\'' +
                ", img_id='" + img_id +'\'' +
                ", img_URL='" + img_URL +'\'' +
                ", like_time='" + like_time +'\'' +
                '}';
    }
}
