package tabian.com.instagramclone2.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;
import tabian.com.instagramclone2.R;
import tabian.com.instagramclone2.models.Following;

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
        TextView username, followingUserName;
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

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.following = getItem(position);
        viewHolder.username.setText(getItem(position).getUser_name());
        //viewHolder.followingUserName.setText(getItem(position).getFollow_name());
        ImageLoader imgLoader = ImageLoader.getInstance();
        //imgLoader.displayImage();

        return convertView;
    }
}
