package tabian.com.instagramclone2.Likes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tabian.com.instagramclone2.R;

public class LikesFeedFragment extends Fragment {
    private static final String TAG = "LIKES FEED FRAGMENT";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_likes, container, false);

        return view;
    }
}
