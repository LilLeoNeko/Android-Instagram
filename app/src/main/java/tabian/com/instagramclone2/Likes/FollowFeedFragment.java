package tabian.com.instagramclone2.Likes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;

import tabian.com.instagramclone2.R;

public class FollowFeedFragment extends Fragment {
    private static final String TAG = "LIKES FEED FRAGMENT";
    private ArrayList<String> followingUsers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_follow, container, false);
        getFollowingUser();
        return view;
    }
    /**
     Get all user id's that current user is following
     */
    private void getFollowingUser() {
        Log.d(TAG, "getFollowing: searching for following");

        clearAll();
        //also add your own id to the list
        //followingUsers.add(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Query query = FirebaseDatabase.getInstance().getReference()
                .child(getActivity().getString(R.string.dbname_following))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "getFollowing: found user: " + singleSnapshot
                            .child(getString(R.string.field_user_id)).getValue());
                    followingUsers.add(singleSnapshot
                            .child(getString(R.string.field_user_id)).getValue().toString());
                }
                getFollowingEvent();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void getFollowingEvent(){
        Log.d(TAG, "getPhotos: getting list of photos");

        for(int i = 0; i < followingUsers.size(); i++) {
            final int count = i;
            Query query = FirebaseDatabase.getInstance().getReference()
                    .child(getActivity().getString(R.string.dbname_following))
                    .child(followingUsers.get(i))
                    .orderByChild(getString(R.string.field_user_id))
                    .equalTo(followingUsers.get(i));
        }
    }
    private void clearAll(){
        if(followingUsers != null){
            followingUsers.clear();
        }
        followingUsers = new ArrayList<>();
    }
}
