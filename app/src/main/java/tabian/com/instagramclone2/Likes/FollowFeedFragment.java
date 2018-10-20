package tabian.com.instagramclone2.Likes;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eschao.android.widget.elasticlistview.OnUpdateListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tabian.com.instagramclone2.R;
import tabian.com.instagramclone2.Utils.FollowListAdaptor;
import tabian.com.instagramclone2.models.Following;

public class FollowFeedFragment extends Fragment implements OnUpdateListener {
    private static final String TAG = "LIKES FEED FRAGMENT";
    private ArrayList<Following> followings;
    private ArrayList<String>followingUsers;
    private FollowListAdaptor listAdaptor;
    private ListView listView;

    @Override
    public void onUpdate() {
        Log.d(TAG, "ElasticListView: updating list view...");
        getFollowingUser();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_follow, container, false);
        listView = view.findViewById(R.id.listView);
        initListViewRefresh();
        getFollowingUser();
        return view;
    }
    private void initListViewRefresh(){
        listView.setAdapter(listAdaptor);
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
    /**
     * According to the user you follow
     * get following event of the user you follow
     * */
    private void getFollowingEvent(){
        Log.d(TAG, "get all following objects");

        for(int i = 0; i < followingUsers.size(); i++) {
            final int count = i;
            Query query = FirebaseDatabase.getInstance().getReference()
                    .child(getActivity().getString(R.string.dbname_following))
                    .child(followingUsers.get(i));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()) {
                        Following newFollow = new Following();
                        Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                        newFollow.setUser_name(objectMap.get(getString(R.string.field_user_name)).toString());
                        newFollow.setUser_id(objectMap.get(getString(R.string.field_user_id)).toString());
                        newFollow.setFollow_id(objectMap.get(getString(R.string.field_follow_id)).toString());
                        newFollow.setFollow_time(objectMap.get(getString(R.string.field_follow_time)).toString());
                        followings.add(newFollow);
                    }
                    if(count >= followingUsers.size()-1){
                        displayFollowEvent();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
    private void clearAll(){
        if(followingUsers != null){
            followingUsers.clear();
        }
        if(followings != null){
            followings.clear();
        }
        followings = new ArrayList<>();
        followingUsers = new ArrayList<>();
    }
    private void displayFollowEvent(){
        if (followings != null){
            listAdaptor = new FollowListAdaptor(getActivity(),R.layout.layout_follow_listitem,followings);
            listView.setAdapter(listAdaptor);
        }
    }
}
