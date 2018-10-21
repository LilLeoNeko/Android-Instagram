package group30.instagram.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


import de.hdodenhof.circleimageview.CircleImageView;
import group30.com.instagramclone2.R;
import group30.instagram.models.UserAccountSettings;
import group30.instagram.models.UserLikePhotos;

public class LikeListAdaptor extends ArrayAdapter<UserLikePhotos> {
    private static final String TAG = "LikeListAdaptor";
    private LayoutInflater layoutInflater;
    private List<UserLikePhotos> likePhotos = null;
    private int layoutResource;
    private Context mContext;

    public LikeListAdaptor(@NonNull Context context, int resource, @NonNull List<UserLikePhotos> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.likePhotos = objects;
        layoutResource = resource;
        mContext = context;
    }
    private static class ViewHolder{
        TextView userGiveLike, userGetLike, timeDiff;
        CircleImageView profileImage;
        SquareImageView likedPhoto;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.userGiveLike = convertView.findViewById(R.id.username);
            viewHolder.userGetLike =  convertView.findViewById(R.id.likedPicUser);
            viewHolder.profileImage = convertView.findViewById(R.id.profile_image);
            viewHolder.likedPhoto =convertView.findViewById(R.id.liked_image);
            viewHolder.timeDiff = convertView.findViewById(R.id.time);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final ImageLoader imgLoader = ImageLoader.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String userGiveLikeid = getItem(position).getUser_who_like();
        String userGetLikeid = getItem(position).getUser_own_pic();
        /******************************************************************************************
         * get the profile image and username who give like
         *****************************************************************************************/
        Query query = reference
                .child(mContext.getString(R.string.dbname_user_account_settings))
                .orderByChild(mContext.getString(R.string.field_user_id))
                .equalTo(userGiveLikeid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    Log.d(TAG, "onDataChange: found user: "
                            + singleSnapshot.getValue(UserAccountSettings.class).getUsername());
                    String checkName = singleSnapshot.getValue(UserAccountSettings.class).getUsername();
                    viewHolder.userGiveLike.setText(singleSnapshot.getValue(UserAccountSettings.class).getUsername());
                    imgLoader.displayImage(singleSnapshot.getValue(UserAccountSettings.class).getProfile_photo(),
                            viewHolder.profileImage);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        /******************************************************************************************
         * get username who get like, set this username to the view
         *****************************************************************************************/
        Query newquery = reference
                .child(mContext.getString(R.string.dbname_user_account_settings))
                .orderByChild(mContext.getString(R.string.field_user_id))
                .equalTo(userGetLikeid);
        newquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    Log.d(TAG, "onDataChange: found user: "
                            + singleSnapshot.getValue(UserAccountSettings.class).getUsername());
                    String checkName = singleSnapshot.getValue(UserAccountSettings.class).getUsername();
                    viewHolder.userGetLike.setText(singleSnapshot.getValue(UserAccountSettings.class).getUsername());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        imgLoader.displayImage(getItem(position).getImg_URL(), viewHolder.likedPhoto);
        //Set Time difference
        String timeDiff = getTimestampDifference(getItem(position));
        if(!timeDiff.equals("0")){
            viewHolder.timeDiff.setText(timeDiff + " DAYS AGO");
        }else{
            viewHolder.timeDiff.setText("TODAY");
        }

        return convertView;
    }
    private String getTimestampDifference(UserLikePhotos userLikePhotos){
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Australia/Victoria"));//google 'android list of timezones'
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String like_time = userLikePhotos.getLike_time();
        try{
            timestamp = sdf.parse(like_time);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
            difference = "0";
        }
        return difference;
    }

}
