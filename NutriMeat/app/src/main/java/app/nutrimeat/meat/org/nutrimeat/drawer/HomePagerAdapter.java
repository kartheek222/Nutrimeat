package app.nutrimeat.meat.org.nutrimeat.drawer;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import app.nutrimeat.meat.org.nutrimeat.R;

/**
 * Created by Admin on 6/7/2017.
 */

public class HomePagerAdapter extends PagerAdapter {
    int[] imageIds = new int[]{R.drawable.ic_pager_scene1, R.drawable.ic_pager_scene2, R.drawable.ic_pager_scene3};

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        RelativeLayout v0 = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.home_viewpager_item, null);
        ImageView imageView = (ImageView) v0.findViewById(R.id.photo_thumb);
        int padding = 10;
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setImageResource(imageIds[position]);
        container.addView(v0, 0);
        return v0;
    }
}
