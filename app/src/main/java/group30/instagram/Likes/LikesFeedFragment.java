package group30.instagram.Likes;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import group30.com.instagramclone2.R;
import group30.instagram.Utils.LikeListAdaptor;
import group30.instagram.models.UserLikePhotos;


public class LikesFeedFragment extends Fragment {
    private static final String TAG = "LIKES FEED FRAGMENT";
    private ArrayList<UserLikePhotos> likes;
    private ArrayList<String>followingUsers;
    private LikeListAdaptor listAdaptor;
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_likes, container, false);
        listView = view.findViewById(R.id.listView);
        initListViewRefresh();
        getFollowingUser();
        return view;
    }
    private void initListViewRefresh(){
        listView.setAdapter(listAdaptor);
    }
    /**
    **Get the following of current user
     **/
    private void getFollowingUser() {
        Log.d(TAG, "getFollowing: searching for following");

        clearAll();
        //
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
                getLikesEvent();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    /**
     **Get likes event of following users
     **/
    private void getLikesEvent() {
        Log.d(TAG, "get all following objects");
        for(int i = 0; i < followingUsers.size(); i++) {
            final int count = i;
            Query query = FirebaseDatabase.getInstance().getReference()
                    .child(getActivity().getString(R.string.dbname_user_like_photos))
                    .child(followingUsers.get(i));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()) {
                        UserLikePhotos newLikePhoto = new UserLikePhotos();
                        Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                        //if (objectMap.get(getString(R.string.)).toString() != null)
                        newLikePhoto.setUser_who_like(objectMap.get(getString(R.string.field_who_give_like)).toString());
                        //if (objectMap.get(getString(R.string.)).toString() != null)
                        newLikePhoto.setUser_own_pic(objectMap.get(getString(R.string.field_who_get_like)).toString());
                        //if (objectMap.get(getString(R.string.)).toString()!= null)
                        newLikePhoto.setImg_id(objectMap.get(getString(R.string.field_img_id)).toString());
                        //if (objectMap.get(getString(R.string.)).toString()!=null)
                        newLikePhoto.setImg_URL(objectMap.get(getString(R.string.field_img_URL)).toString());
                        //if (objectMap.get(getString(R.string.)).toString()!=null)
                        newLikePhoto.setLike_time(objectMap.get(getString(R.string.field_like_time)).toString());
                        likes.add(newLikePhoto);
                    }
                    if(count >= followingUsers.size()-1){
                        displayLikesEvent();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    private void displayLikesEvent(){
        if (likes != null){
            listAdaptor = new LikeListAdaptor(getActivity(),R.layout.layout_like_listitem,likes);
            listView.setAdapter(listAdaptor);
        }
    }
    private void clearAll(){
        if(followingUsers != null){
            followingUsers.clear();
        }
        if(likes != null){
            likes.clear();
        }
        likes = new ArrayList<>();
        followingUsers = new ArrayList<>();
    }
}
