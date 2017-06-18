package app.nutrimeat.meat.org.nutrimeat.drawer;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import app.nutrimeat.meat.org.nutrimeat.Home.StatsResponseModel;
import app.nutrimeat.meat.org.nutrimeat.Navdrawer;
import app.nutrimeat.meat.org.nutrimeat.R;
import app.nutrimeat.meat.org.nutrimeat.Recipies.RecipiesFragment;
import app.nutrimeat.meat.org.nutrimeat.WatsCooking.WatsCookingFragment;
import app.nutrimeat.meat.org.nutrimeat.api.API;
import app.nutrimeat.meat.org.nutrimeat.product.ProductsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.facebook.FacebookSdk.getApplicationContext;


public class Recipes extends Fragment implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private TextView tvDeliveryStatus, tvHappyCustomers, tvKilosSold;

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
        tvKilosSold = (TextView) view.findViewById(R.id.tvKilosSold);
        tvHappyCustomers = (TextView) view.findViewById(R.id.tvHappyCustomers);
        tvDeliveryStatus = (TextView) view.findViewById(R.id.tvOrdersDelivered);
        if (((Navdrawer) getActivity()).getStatsResponseModel() == null) {
            reqestRecipeStatus();
        } else {
            StatsResponseModel responseModel = ((Navdrawer) getActivity()).getStatsResponseModel();
            tvDeliveryStatus.setText(responseModel.getDelivered());
            tvKilosSold.setText(responseModel.getSold());
            tvHappyCustomers.setText(responseModel.getHappycustomers());

        }
        return view;
    }

    private void reqestRecipeStatus() {
        progressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Logging User...", true);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<StatsResponseModel> responseCall = api.getStats();
        responseCall.enqueue(new Callback<StatsResponseModel>() {
            @Override
            public void onResponse(Call<StatsResponseModel> call, Response<StatsResponseModel> response) {
                StatsResponseModel responseModel = response.body();
                progressDialog.dismiss();
                if (responseModel != null) {
                    if (getActivity() != null) {
                        ((Navdrawer) getActivity()).setStatsResponseModel(responseModel);
                    }
                    tvDeliveryStatus.setText(responseModel.getDelivered());
                    tvKilosSold.setText(responseModel.getSold());
                    tvHappyCustomers.setText(responseModel.getHappycustomers());
                }
            }

            @Override
            public void onFailure(Call<StatsResponseModel> call, Throwable t) {
                Log.d("RESPONSE_Login", "onFailure: " + t.getMessage());
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Internal error. Please Try Again", Toast.LENGTH_SHORT).show();
            }
        });
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
                ((Navdrawer) getActivity()).getSupportActionBar().setTitle("Recipes");
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