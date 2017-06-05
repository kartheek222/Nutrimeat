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
import android.widget.Toast;

import com.sasidhar.smaps.payumoney.MakePaymentActivity;
import com.sasidhar.smaps.payumoney.PayUMoney_Constants;
import com.sasidhar.smaps.payumoney.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
    private CheckoutAdapter checkoutAdapter;

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
                checkoutAdapter = new CheckoutAdapter(listOfStrings, R.layout.checkout_item, CheckoutActivity.this);
                Checkout_rv.setAdapter(checkoutAdapter);
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
        HashMap params = new HashMap<>();
        params.put(PayUMoney_Constants.KEY, "m3pWGL"); // Get merchant key from PayU Money Account
        params.put(PayUMoney_Constants.TXN_ID, UUID.randomUUID().toString());
        params.put(PayUMoney_Constants.AMOUNT, checkoutAdapter.getSub_total());
        params.put(PayUMoney_Constants.PRODUCT_INFO, "product_info");
        params.put(PayUMoney_Constants.FIRST_NAME, "first_name");
        params.put(PayUMoney_Constants.EMAIL, "email");
        params.put(PayUMoney_Constants.PHONE, "phone_number");
        params.put(PayUMoney_Constants.SURL, "success_url");
        params.put(PayUMoney_Constants.FURL, "failure_url");


// User defined fields are optional (pass empty string)
        /*params.put(PayUMoney_Constants.UDF1, "");
        params.put(PayUMoney_Constants.UDF2, "");
        params.put(PayUMoney_Constants.UDF3, "");
        params.put(PayUMoney_Constants.UDF4, "");
        params.put(PayUMoney_Constants.UDF5, "");*/


// generate hash by passing params and salt
        String hash = Utils.generateHash(params, "RbHCQkbb"); // Get Salt from PayU Money Account
        params.put(PayUMoney_Constants.HASH, hash);


// SERVICE PROVIDER VALUE IS ALWAYS "payu_paisa".
        params.put(PayUMoney_Constants.SERVICE_PROVIDER, "payu_paisa");


        Intent intent = new Intent(this, MakePaymentActivity.class);
        intent.putExtra(PayUMoney_Constants.ENVIRONMENT, PayUMoney_Constants.ENV_DEV);
        intent.putExtra(PayUMoney_Constants.PARAMS, params);
        startActivityForResult(intent, PayUMoney_Constants.PAYMENT_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PayUMoney_Constants.PAYMENT_REQUEST) {
            switch (resultCode) {
                case RESULT_OK:
                    Toast.makeText(getApplicationContext(), "Payment Success.", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(getApplicationContext(), "Payment Cancelled | Failed.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
