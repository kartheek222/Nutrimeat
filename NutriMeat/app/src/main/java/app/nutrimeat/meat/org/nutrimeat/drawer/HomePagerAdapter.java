package app.nutrimeat.meat.org.nutrimeat.drawer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import app.nutrimeat.meat.org.nutrimeat.R;

/**
 * Created by Admin on 6/7/2017.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    int[] imageIds = new int[]{R.drawable.ic_pager_scene1, R.drawable.ic_pager_scene2, R.drawable.ic_pager_scene3};

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }


    @Override
    public Fragment getItem(int position) {
        return HomeViewPagerFragment.getInstance(imageIds[position]);
    }
}
