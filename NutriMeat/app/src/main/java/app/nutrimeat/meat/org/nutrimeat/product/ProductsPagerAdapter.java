package app.nutrimeat.meat.org.nutrimeat.product;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Admin on 6/6/2017.
 */

public class ProductsPagerAdapter extends FragmentPagerAdapter {
    public ProductsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Products.newInstance("chicken");
            case 1:
                return Products.newInstance("mutton");
            case 2:
                return Products.newInstance("sea foods");
            case 3:
                return Products.newInstance("others");
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "CHICKEN";
            case 1:
                return "MEAT";
            case 2:
                return "SEA FOOD";
            case 3:
                return "OTHERS";
        }
        return super.getPageTitle(position);
    }
}
