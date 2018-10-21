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
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import group30.com.instagramclone2.R;
import group30.instagram.models.Following;
import group30.instagram.models.UserAccountSettings;

public class FollowListAdaptor extends ArrayAdapter<Following> {
    private static final String TAG = "FollowListAdaptor";
    private LayoutInflater layoutInflater;
    private List<Following> followings = null;
    private int layoutResource;
    private Context mContext;

    public FollowListAdaptor(@NonNull Context context, int resource, @NonNull List<Following> objects) {
        super(context, resource, objects);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.followings = objects;
        layoutResource = resource;
        mContext = context;
    }
    private static class ViewHolder{
        TextView username, followingUserName, timeDiff;
        CircleImageView profileImage;
        Following following;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.username = convertView.findViewById(R.id.username);
            viewHolder.followingUserName =  convertView.findViewById(R.id.followUserName);
            viewHolder.profileImage = convertView.findViewById(R.id.profile_image);
            viewHolder.timeDiff = convertView.findViewById(R.id.time);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        /**
         * get the profile image and username
         */
        final ImageLoader imgLoader = ImageLoader.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String followingUserid = getItem(position).getFollow_id();
        Query query = reference
                .child(mContext.getString(R.string.dbname_user_account_settings))
                .orderByChild(mContext.getString(R.string.field_user_id))
                .equalTo(followingUserid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    Log.d(TAG, "onDataChange: found user: "
                            + singleSnapshot.getValue(UserAccountSettings.class).getUsername());
                    String checkName = singleSnapshot.getValue(UserAccountSettings.class).getUsername();
                    viewHolder.username.setText(singleSnapshot.getValue(UserAccountSettings.class).getUsername());

                    imgLoader.displayImage(singleSnapshot.getValue(UserAccountSettings.class).getProfile_photo(),
                            viewHolder.profileImage);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        viewHolder.following = getItem(position);
        viewHolder.followingUserName.setText(getItem(position).getUser_name());
        String timeDiff = getTimestampDifference(getItem(position));
        if(!timeDiff.equals("0")){
            viewHolder.timeDiff.setText(timeDiff + " DAYS AGO");
        }else{
            viewHolder.timeDiff.setText("TODAY");
        }

        return convertView;
    }
    private String getTimestampDifference(Following following){
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Australia/Victoria"));//google 'android list of timezones'
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String follow_time = following.getFollow_time();
        try{
            timestamp = sdf.parse(follow_time);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
            difference = "0";
        }
        return difference;
    }
}
