package app.nutrimeat.meat.org.nutrimeat.drawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import app.nutrimeat.meat.org.nutrimeat.R;

/**
 * Created by kartheek.sabbisetty on 6/8/2017.
 */

public class HomeViewPagerFragment extends Fragment {
    public static HomeViewPagerFragment getInstance(int resourceId) {
        HomeViewPagerFragment fragment = new HomeViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("resourceId", resourceId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout v0 = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.home_viewpager_item, container, false);
        ImageView imageView = (ImageView) v0.findViewById(R.id.photo_thumb);
        imageView.setImageResource(getArguments().getInt("resourceId"));
        return v0;
    }
}
