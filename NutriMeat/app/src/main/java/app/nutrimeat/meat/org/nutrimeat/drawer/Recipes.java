package app.nutrimeat.meat.org.nutrimeat.drawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.nutrimeat.meat.org.nutrimeat.Navdrawer;
import app.nutrimeat.meat.org.nutrimeat.R;
import app.nutrimeat.meat.org.nutrimeat.Recipies.RecipiesFragment;
import app.nutrimeat.meat.org.nutrimeat.WatsCooking.WatsCookingFragment;
import app.nutrimeat.meat.org.nutrimeat.product.ProductsFragment;


public class Recipes extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.recipes_sample, container, false);
        ViewPager viewpager = (ViewPager) view.findViewById(R.id.viewPager);
        viewpager.setAdapter(new HomePagerAdapter(getChildFragmentManager()));
        view.findViewById(R.id.tvProducts).setOnClickListener(this);
        view.findViewById(R.id.tvRecipies).setOnClickListener(this);
        view.findViewById(R.id.tvWhatsCooking).setOnClickListener(this);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Recipes");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvProducts:
                //replacing the fragment
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, ProductsFragment.newInstance());
                ft.commit();
                ((Navdrawer) getActivity()).getSupportActionBar().setTitle("Products");
                break;
            case R.id.tvRecipies:
                //replacing the fragment
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new RecipiesFragment());
                ft.commit();
                ((Navdrawer) getActivity()).getSupportActionBar().setTitle("Recipies");
                break;
            case R.id.tvWhatsCooking:
                //replacing the fragment
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new WatsCookingFragment());
                ft.commit();
                ((Navdrawer) getActivity()).getSupportActionBar().setTitle("What's Cooking");
                break;
        }
    }
}