package app.nutrimeat.meat.org.nutrimeat.Checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import app.nutrimeat.meat.org.nutrimeat.CommonFunctions;
import app.nutrimeat.meat.org.nutrimeat.R;
import app.nutrimeat.meat.org.nutrimeat.Textview.p_MyCustomTextView_mbold;
import app.nutrimeat.meat.org.nutrimeat.Textview.p_MyCustomTextView_regular;
import app.nutrimeat.meat.org.nutrimeat.product.ModelCart;

import static app.nutrimeat.meat.org.nutrimeat.PrefManager.PREF_PRODUCT_CART;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    // ArrayList<ModelCart> ListofProdcuts = new ArrayList<>();
    RecyclerView Checkout_rv;

    ArrayList<Double> price = new ArrayList<>();
    //public double price = 0;
    p_MyCustomTextView_regular subtotal;
    private Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subtotal = (p_MyCustomTextView_regular) findViewById(R.id.subtotal);
        p_MyCustomTextView_mbold empty_view = (p_MyCustomTextView_mbold) findViewById(R.id.empty_view);
        Checkout_rv = (RecyclerView) findViewById(R.id.Checkout_rv);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(this);
        final List<ModelCart> cart_itens = CommonFunctions.getSharedPreferenceProductList(CheckoutActivity.this, PREF_PRODUCT_CART);
        if (cart_itens != null) {
            ArrayList<ModelCart> listOfStrings = new ArrayList<>(cart_itens.size());
            listOfStrings.addAll(cart_itens);
            //ListofProdcuts = (ArrayList<ModelCart>) getIntent().getBundleExtra("bundle").getSerializable("isadd_to_cart");
            Checkout_rv.setLayoutManager(new LinearLayoutManager(this));
            if (listOfStrings.size() > 0) {
                empty_view.setVisibility(View.GONE);
                Checkout_rv.setVisibility(View.VISIBLE);
                CheckoutAdapter adapter = new CheckoutAdapter(listOfStrings, R.layout.checkout_item, CheckoutActivity.this);
                Checkout_rv.setAdapter(adapter);
            } else {
                empty_view.setVisibility(View.VISIBLE);
                Checkout_rv.setVisibility(View.GONE);
            }
        } else {
            empty_view.setVisibility(View.VISIBLE);
            Checkout_rv.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("product", "product");
        intent.putExtra("position", "two");
        setResult(Activity.RESULT_OK, intent);
        finish();
        //  super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCheckout:
                final List<ModelCart> cart_itens = CommonFunctions.getSharedPreferenceProductList(CheckoutActivity.this, PREF_PRODUCT_CART);
                if (cart_itens != null && cart_itens.size() > 0) {
                    navigateUserToPayment(cart_itens);
                }
                break;
        }
    }

    private void navigateUserToPayment(List<ModelCart> cart_itens) {
        
    }
}
